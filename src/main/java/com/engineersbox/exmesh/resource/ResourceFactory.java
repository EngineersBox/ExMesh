package com.engineersbox.exmesh.resource;

public interface ResourceFactory<T extends AllocatableResource> {

    /**
     * Create a new allocatable resource
     * @return Resource instance
     */
    T provision();

    /**
     * Create a number of allocatable resources
     * @param count Number of resources to allocate
     * @return Instantiated resources
     */
    Iterable<T> provision(final int count);

}
