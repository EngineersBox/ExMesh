package com.engineersbox.exmesh.scheduling.algorithm;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.Scheduler;
import com.engineersbox.exmesh.scheduling.allocation.Allocator;
import org.jctools.queues.SpscLinkedQueue;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Arrays;

public class SimpleScheduler extends Scheduler {

    private final SpscLinkedQueue<Task<?,?,?,?>> queue;

    public SimpleScheduler(final Allocator allocator) {
        super(allocator);
        this.queue = new SpscLinkedQueue<>();
    }

    @Override
    public void submit(Task<?, ?, ?, ?>... tasks) {
        Arrays.stream(tasks).forEach(this.queue::offer);
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
    public void issue() {
        final Task<?,?,?,?> task = this.queue.poll();
        if (task != null) {
            super.allocator.allocate(task);
        }
    }
}
