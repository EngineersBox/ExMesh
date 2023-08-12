package com.engineersbox.exmesh.scheduling.allocation;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.resource.AllocatableResource;
import com.engineersbox.exmesh.resource.ResourceFactory;

@FunctionalInterface
public interface Allocator {

    /**
     * Allocate resource to task
     * @param resourceFactory Provider to provision a resource instance
     * @param task Instance of task to be executed under resource
     */
    AllocatableResource allocate(final ResourceFactory<? extends AllocatableResource> resourceFactory,
                                 final Task<?, ?, ?, ?> task);

}