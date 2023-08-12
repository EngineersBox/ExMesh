package com.engineersbox.exmesh.scheduling.algorithm;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.Scheduler;
import org.eclipse.collections.api.RichIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarpInterleavedScheduler extends Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarpInterleavedScheduler.class);
    private static final int CLUSTER_COUNT = 3;

    private final ExecutionBehaviourDecisionUnit ebdu;

    public WarpInterleavedScheduler(final int nodeCount,
                                    final int containerCount,
                                    final int stackSize) {
        // Grid has a single bitmask of size nodeCount
        // Each node has a bitmask of size containerCount
        this.ebdu = new ExecutionBehaviourDecisionUnit(nodeCount, containerCount, stackSize);
    }

    @Override
    public void submit(final Mesh<? extends Pipe> mesh) {

    }

    @Override
    public void analyse() {

    }

    @Override
    public void mark() {

    }

    @Override
    public RichIterable<Task<?,?,?,?>> issue() {
        // 1. Get next waiting tasks in mesh
        // 2. Group tasks into nodes based on common parents and dependency
        // 3. Issue task groups to EBDU
        // 4. Allocate node to groups masked in
        // 5. Allocate containers to tasks masked in
        // 6. Mark tasks with active mask (1s in bitmask) as executing
        // 7. Reject non-masked in tasks
        return null;
    }

}
