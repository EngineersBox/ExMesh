package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;

public class Scheduler {

    private final Mesh<Pipe> mesh;

    public Scheduler(final Mesh<Pipe> mesh) {
        this.mesh = mesh;
    }

}
