package com.engineersbox.exmesh.resource;

public interface ResourceFactory<T extends AllocatableResource> {

    T provision();

    Iterable<T> provision(final int count);

}
