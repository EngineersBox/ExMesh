package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.scheduling.allocation.Allocator;

public abstract class Scheduler {

    protected final Allocator allocator;

    protected Scheduler(final Allocator allocator) {
        this.allocator = allocator;
    }

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
