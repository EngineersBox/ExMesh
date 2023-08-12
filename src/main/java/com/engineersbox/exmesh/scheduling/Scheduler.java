package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import org.eclipse.collections.api.RichIterable;

public abstract class Scheduler {

    /**
     * Submit a mesh of tasks to schedule.
     * @param mesh Mesh instance
     */
    public abstract void submit(final Mesh<? extends Pipe> mesh);

    /**
     * Determine the candidacy of tasks ready to be issued
     * and prioritise them based on scheduler criteria/behaviour.
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
    public abstract RichIterable<Task<?,?,?,?>> issue();

    public RichIterable<Task<?,?,?,?>> executeIteration() {
        this.analyse();
        this.mark();
        return this.issue();
    }

}
