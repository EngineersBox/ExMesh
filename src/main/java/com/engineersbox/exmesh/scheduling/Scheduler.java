package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import org.jgrapht.alg.clustering.KSpanningTreeClustering;
import org.jgrapht.traverse.TopologicalOrderIterator;

public class Scheduler {

    private static final int CLUSTER_COUNT = 10;

    private final Mesh<Pipe> mesh;
    private final TopologicalOrderIterator<Task<?,?,?,?>, Pipe> meshIterator;
    private final KSpanningTreeClustering<Task<?,?,?,?>, Pipe> meshClustering;

    private final ExecutionBehaviourDecisionUnit ebdu;

    public Scheduler(final Mesh<Pipe> mesh,
                     final int nodeCount,
                     final int containerCount,
                     final int stackSize) {
        // Grid has a single bitmask of size nodeCount
        // Each node has a bitmask of size containerCount
        this.mesh = mesh;
        this.meshIterator = new TopologicalOrderIterator<>(mesh);
        this.meshClustering = new KSpanningTreeClustering<>(mesh, CLUSTER_COUNT);
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
