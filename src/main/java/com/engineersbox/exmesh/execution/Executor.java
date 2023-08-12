package com.engineersbox.exmesh.execution;

import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.resource.AllocatableResource;
import com.engineersbox.exmesh.resource.ResourceFactory;
import com.engineersbox.exmesh.scheduling.Scheduler;
import com.engineersbox.exmesh.scheduling.allocation.Allocator;
import org.eclipse.collections.api.RichIterable;
import org.jctools.queues.SpscArrayQueue;
import org.jctools.queues.atomic.SpscAtomicArrayQueue;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Future;

public class Executor implements Runnable {

    private final Scheduler scheduler;
    private final ResourceFactory<? extends AllocatableResource> resourceFactory;
    private final Allocator allocator;
    private final SpscAtomicArrayQueue<Mesh<? extends Pipe>> meshes;

    public Executor(final Scheduler scheduler,
                    final ResourceFactory<? extends AllocatableResource> resourceFactory,
                    final Allocator allocator,
                    final int concurrentExecutionCount) {
        this.scheduler = scheduler;
        this.resourceFactory = resourceFactory;
        this.allocator = allocator;
        this.meshes = new SpscAtomicArrayQueue<>(concurrentExecutionCount);
    }

    public <E extends Pipe> boolean submit(final Mesh<E> mesh) {
        if (this.meshes.offer(mesh)) {
            this.scheduler.submit(mesh);
            return true;
        }
        return false;
    }

    private StateManager allocateResource(final Task<?,?,?,?> task) {
        task.transitionState();
        return new StateManager(
                task,
                this.allocator.allocate(
                        this.resourceFactory,
                        task
                )
        );
    }

    private StateManager consolidate(final StateManager stateManager) {
        final Task<?,?,?,?> task = stateManager.task;
        final Mesh<? extends Pipe> mesh = task.getParent();
        final Set<? extends Pipe> pipes = mesh.incomingEdgesOf(task);
        if (!pipes.isEmpty()) {
            task.getIngressPolicy().interfaceWith(
                    mesh.incomingEdgesOf(task),
                    task
            );
        }
        return stateManager;
    }

    private StateManager initialiseResource(final StateManager stateManager) {
        final AllocatableResource resource = stateManager.resource;
        resource.startup();
        resource.configure();
        return stateManager;
    }

    private StateManager executeTask(final StateManager stateManager) {
        stateManager.task.transitionState();
        stateManager.result = stateManager.resource.apply(stateManager.task);
        stateManager.task.transitionState();
        return stateManager;
    }

    private StateManager split(final StateManager stateManager) {
        final Task<?,?,?,?> task = stateManager.task;
        final Mesh<? extends Pipe> mesh = task.getParent();
        final Set<? extends Pipe> pipes = mesh.outgoingEdgesOf(task);
        if (!pipes.isEmpty()) {
            task.getEgressPolicy().interfaceWith(
                    mesh.outgoingEdgesOf(task),
                    task
            );
        }
        return stateManager;
    }

    private void cleanupResource(final StateManager stateManager) {
        final AllocatableResource resource = stateManager.resource;
        resource.cleanup();
    }

    @Override
    public void run() {
        RichIterable<Task<?,?,?,?>> tasks;
        while ((tasks = this.scheduler.executeIteration()) != null && !tasks.isEmpty()) {
            /* 1. Run scheduler iteration (analyse, mark, issue)
             * 2. Allocate resources to issued tasks (if allocation condition is met)
             * 3. Configure resources for tasks
             * 4. Invoke execution
             * 5. Run cleanup for tasks on resources
             */
            final RichIterable<StateManager> resources = tasks.collect(this::allocateResource)
                    .collect(this::consolidate)
                    .collect(this::initialiseResource)
                    .collect(this::executeTask);
            final SpscArrayQueue<Future<ExecutionResult>> futures = new SpscArrayQueue<>(resources.size());
            resources.select((final StateManager stateManager) -> Objects.nonNull(stateManager.result))
                    .each((final StateManager stateManager) -> futures.offer(stateManager.result));
            Future<ExecutionResult> future;
            while ((future = futures.poll()) != null) {
                if (!future.isDone()) {
                    futures.offer(future);
                }
            }
            resources.collect(this::split)
                    .each(this::cleanupResource);
        }
    }

    private static class StateManager implements AllocatableResource {

        private final Task<?,?,?,?> task;
        private Future<ExecutionResult> result;
        private final AllocatableResource resource;
        private boolean allocated = false;
        private boolean deallocated = false;

        public StateManager(final Task<?,?,?,?> task,
                            final AllocatableResource resource) {
            this.task = task;
            this.resource = resource;
        }

        @Override
        public void startup() {
            if (!this.allocated) {
                this.resource.startup();
                this.allocated = true;
            }
        }

        @Override
        public void configure() {
            this.resource.configure();
        }

        @Override
        public Future<ExecutionResult> apply(final Task<?, ?, ?, ?> task) {
            return this.resource.apply(task);
        }

        @Override
        public void cleanup() {
            this.resource.cleanup();
        }

        @Override
        public void teardown() {
            if (!this.deallocated) {
                this.resource.teardown();
                this.deallocated = true;
            }
        }
    }
}
