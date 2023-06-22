package com.engineersbox.exmesh.allocation;

import com.engineersbox.exmesh.graph.Mesh;

public interface Allocator {

    /**
     * Allocate resources to mesh nodes according to colouring and topological sorting.
     * @param mesh Mesh instance to reference
     * @param <E> Edge type
     */
    <E> void allocate(final Mesh<E> mesh);

}
