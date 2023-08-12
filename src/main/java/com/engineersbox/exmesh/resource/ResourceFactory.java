package com.engineersbox.exmesh.resource;

@FunctionalInterface
public interface ResourceFactory<T extends AllocatableResource> {

    /**
     * Create a new allocatable resource
     * @return Resource instance
     */
    T provision();

}
