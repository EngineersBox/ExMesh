package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;

public class Scheduler {

    private final Mesh<Pipe> mesh;
    private final ExecutionBehaviourDecisionUnit ebdu;

    public Scheduler(final Mesh<Pipe> mesh,
                     final int nodeCount,
                     final int containerCount,
                     final int stackSize) {
        this.mesh = mesh;
        this.ebdu = new ExecutionBehaviourDecisionUnit(nodeCount, containerCount, stackSize);
    }

    public synchronized void issue() {
        // 1. Get next waiting tasks in mesh
        // 2. Group tasks into nodes based on common parents and dependency
        // 3. Issue task groups to EBDU
        // 4. Allocate node to groups masked in
        // 5. Allocate containers to tasks masked in
        // 6. Mark tasks with active mask (1s in bitmask) as executing
        // 7. Reject non-masked in tasks
    }

}
