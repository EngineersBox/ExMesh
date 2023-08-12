package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.allocation.Allocator;

import java.util.Collection;

public abstract class Scheduler {

    protected final Allocator allocator;

    protected Scheduler(final Allocator allocator) {
        this.allocator = allocator;
    }

    /**
     * Submit a set of tasks to be scheduled.
     * @param tasks Task set
     */
    public abstract void submit(final Collection<Task<?,?,?,?>> tasks);

    /**
     * Submit a mesh of tasks to schedule.
     * @param mesh Mesh instance
     */
    public abstract void submit(final Mesh<? extends Pipe> mesh);

    /**
     * Determine the candidacy of tasks ready to be issued
     * and prioritse them based on scheduler criteria/behaviour.
     */
    public abstract void analyse();

    /**
     * Mark tasks for scheduling and assign issue order in issue
     * queues. Allocate resources via provided allocator SPI.
     */
    public abstract void mark();

    /**
     * Issue next batch of tasks to resources with execution context.
     */
    public abstract void issue();

}
