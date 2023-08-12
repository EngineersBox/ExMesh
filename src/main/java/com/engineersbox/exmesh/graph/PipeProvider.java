package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;

@FunctionalInterface
public interface PipeProvider {

    <E extends Pipe> E provide(final Task<?,?,?,?> sourceVertex,
                               final Task<?,?,?,?> destinationVertex);

}
