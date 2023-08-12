package com.engineersbox.exmesh;

import com.engineersbox.exmesh.execution.ExecutionResult;
import com.engineersbox.exmesh.execution.Executor;
import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.execution.dependency.AllocationStrategy;
import com.engineersbox.exmesh.execution.dependency.SchedulingBehaviour;
import com.engineersbox.exmesh.execution.dependency.ExecutionCondition;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.resource.AllocatableResource;
import com.engineersbox.exmesh.resource.ResourceFactory;
import com.engineersbox.exmesh.scheduling.Scheduler;
import com.engineersbox.exmesh.scheduling.algorithm.SimpleScheduler;
import com.engineersbox.exmesh.scheduling.algorithm.WarpInterleavedScheduler;
import com.engineersbox.exmesh.scheduling.allocation.Allocator;
import com.google.common.collect.Iterables;
import org.eclipse.collections.api.factory.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static class TaskA extends Task<String, Void, List<Double>, Double> {

        public TaskA() {
            super("A", 1.0);
        }

        @Override
        public void invoke() {}

        @Override
        public Void consolidateSingleton(final Iterable<String> values) {
            return null;
        }

        @Override
        public Void consolidateCollection(final Iterable<Void> collections) {
            return null;
        }

        @Override
        public Double splitSingleton() {
            LOGGER.info("[A] Splitting singleton");
            return 1.0d;
        }

        @Override
        public List<Double> splitCollection(int count) {
            LOGGER.info("[A] Splitting collection of size {}", count);
            return List.of(1.0d);
        }

        @Override
        public int internalCollectionSize() {
            return 2;
        }
    }

    private static class TaskB extends Task<List<Double>, List<Double>, Iterable<Integer>, Iterable<Integer>> {

        public TaskB() {
            super("B", 1.0);
        }

        @Override
        public void invoke() {}

        @Override
        public List<Double> consolidateSingleton(final Iterable<List<Double>> values) {
            LOGGER.info("[B] Consolidating {} singletons", Iterables.size(values));
            return null;
        }

        @Override
        public List<Double> consolidateCollection(final Iterable<List<Double>> collections) {
            return null;
        }

        @Override
        public Iterable<Integer> splitSingleton() {
            LOGGER.info("[B] Splitting singleton");
            return Lists.fixedSize.of(1);
        }

        @Override
        public Iterable<Integer> splitCollection(int count) {
            return null;
        }

        @Override
        public int internalCollectionSize() {
            return 0;
        }
    }

    private static class TaskC extends Task<Double, Iterable<Double>, Collection<Integer>, Collection<Integer>> {

        public TaskC() {
            super("C", 1.0);
        }

        @Override
        public void invoke() {}

        @Override
        public Collection<Double> consolidateSingleton(final Iterable<Double> values) {
            return null;
        }

        @Override
        public Collection<Double> consolidateCollection(final Iterable<Iterable<Double>> collections) {
            LOGGER.info("[C] Consolidating {} collections", Iterables.size(collections));
            return null;
        }

        @Override
        public Collection<Integer> splitSingleton() {
            return null;
        }

        @Override
        public Collection<Integer> splitCollection(int count) {
            LOGGER.info("[C] Splitting collection of size {}", count);
            return Lists.fixedSize.of(1);
        }

        @Override
        public int internalCollectionSize() {
            return 0;
        }
    }

    @SchedulingBehaviour(
            strategy = AllocationStrategy.ANY_WITH_ONE,
            condition = ExecutionCondition.PIPELINED
    )
    private static class TaskD extends Task<Integer, Iterable<Integer>, Void, Void> {

        public TaskD() {
            super("D", 1.0);
        }

        @Override
        public void invoke() {}

        @Override
        public Iterable<Integer> consolidateSingleton(final Iterable<Integer> values) {
            LOGGER.info("[D] Consolidating singleton");
            return Lists.fixedSize.of();
        }

        @Override
        public Iterable<Integer> consolidateCollection(final Iterable<Iterable<Integer>> collections) {
            LOGGER.info("[D] Consolidating {} collections", Iterables.size(collections));
            return Lists.fixedSize.of();
        }

        @Override
        public Void splitSingleton() {
            return null;
        }

        @Override
        public Void splitCollection(int count) {
            return null;
        }

        @Override
        public int internalCollectionSize() {
            return 0;
        }
    }

    private static final class TestTask<IS,IC,OC,OS> extends Task<IS,IC,OC,OS> {

        public TestTask() {
            super("E", 1.0);
        }

        @Override
        public void invoke() {}

        @Override
        public IC consolidateSingleton(final Iterable<IS> values) {
            return null;
        }

        @Override
        public IC consolidateCollection(final Iterable<IC> collections) {
            return null;
        }

        @Override
        public OS splitSingleton() {
            return null;
        }

        @Override
        public OC splitCollection(int count) {
            return null;
        }

        @Override
        public int internalCollectionSize() {
            return 0;
        }
    }

    private static final class TestResource implements AllocatableResource {

        @Override
        public void startup() {

        }

        @Override
        public void configure() {

        }

        @Override
        public Future<ExecutionResult> apply(final Task<?, ?, ?, ?> task) {
            LOGGER.info("Executing: {}", task.getName());
            task.invoke();
            return null;
        }

        @Override
        public void cleanup() {

        }

        @Override
        public void teardown() {

        }
    }

    public static void main(final String[] args) {
        final Mesh<Pipe> mesh = new Mesh<>(() -> new Pipe(5));
        final TaskA A = new TaskA();
        final TaskB B = new TaskB();
        final TaskC C = new TaskC();
        final TaskD D = new TaskD();
//        final TestTask<String, Integer, Double, Float> E = new TestTask<>();
        mesh.addVertex(A);
        mesh.addVertex(B);
        mesh.addVertex(C);
        mesh.addVertex(D);
//        mesh.addVertex(E);
        mesh.addEdgeC2S(A, B);
        mesh.addEdgeS2S(A, C);
        mesh.addEdgeS2C(B, D);
        mesh.addEdgeC2C(C, D);
//        mesh.addEdgeC2C(C, new TestTask<String, Collection<Integer>,Void,Void>());

//        final Scheduler warpInterleavedScheduler = new WarpInterleavedScheduler(
//                4,
//                2,
//                10
//        );
        final Scheduler scheduler = new SimpleScheduler();
        final ResourceFactory<? extends AllocatableResource> resourceFactory = (ResourceFactory<AllocatableResource>) TestResource::new;
        final Allocator allocator = (final ResourceFactory<? extends AllocatableResource> rf, final Task<?,?,?,?> task) -> rf.provision();
        final Executor executor = new Executor(
                scheduler,
                resourceFactory,
                allocator,
                1
        );
        executor.submit(mesh);
        executor.run();
    }

}
