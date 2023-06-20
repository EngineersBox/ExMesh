package com.engineersbox.exmesh.resource;

import org.eclipse.collections.api.RichIterable;

public interface ResourceFactory<T extends AllocatableResource> {

    T provision();

    RichIterable<T> provision(final int count);

}
