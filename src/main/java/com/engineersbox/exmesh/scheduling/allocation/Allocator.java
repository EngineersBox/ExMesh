package com.engineersbox.exmesh.scheduling.allocation;

import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;

public interface Allocator {

    /**
     * Allocate resources to mesh nodes according to colouring and topological sorting.
     * @param mesh Mesh instance to reference
     * @param <E> Edge type
     */
    <E extends Pipe> void allocate(final Mesh<E> mesh);

}