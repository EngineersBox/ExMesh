package com.engineersbox.exmesh.execution.type;

import javax.annotation.Nonnull;

public interface Splittable<OS, OC> {

    /**
     * Provide a single value from an internal collection or singleton to send to a task. Implementation should not
     * provide nulls, use {@link java.util.Optional} wrappers to indicate absent elements.
     * @return Singleton element (non-null)
     */
    @Nonnull
    OS splitSingleton();

    /**
     * Provide a collection from an internal collection or singleton to send to a task. Implementation should not
     * provide nulls, use {@link java.util.Optional} wrappers to indicate absent elements.
     * @param partitionCount Number of elements in the collection
     * @return Collection of elements (non-null)
     */
    @Nonnull
    OC splitCollection(final int partitionCount);

    /**
     * Provide total size of internal collection storing task results
     * @return Collection size
     */
    int internalCollectionSize();

}
