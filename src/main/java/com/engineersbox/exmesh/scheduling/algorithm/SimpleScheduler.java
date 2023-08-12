package com.engineersbox.exmesh.scheduling.algorithm;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.Scheduler;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.jctools.queues.SpscLinkedQueue;
import org.jgrapht.traverse.BreadthFirstIterator;

public class SimpleScheduler extends Scheduler {

    private final SpscLinkedQueue<Task<?,?,?,?>> queue;

    public SimpleScheduler() {
        this.queue = new SpscLinkedQueue<>();
    }

    @Override
    public void submit(final Mesh<? extends Pipe> mesh) {
        final BreadthFirstIterator<Task<?,?,?,?>, ? extends Pipe> iterator = new BreadthFirstIterator<>(mesh);
        iterator.forEachRemaining(this.queue::offer);
    }

    @Override
    public void analyse() {

    }

    @Override
    public void mark() {

    }

    @Override
    public RichIterable<Task<?,?,?,?>> issue() {
        final Task<?,?,?,?> task = this.queue.poll();
        return task == null ? Lists.fixedSize.of() : Lists.immutable.of(task);
    }
}
