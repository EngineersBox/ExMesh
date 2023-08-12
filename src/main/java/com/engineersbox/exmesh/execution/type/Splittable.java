package com.engineersbox.exmesh.execution.type;

public interface Splittable<OS, OC> {

    /**
     * Provide a single value from an internal collection or singleton to send to a task
     * @return Singleton element
     */
    OS splitSingle();

    /**
     * Provide a collection from an internal collection or singleton to send to a task
     * @param partitionCount Number of elements in the collection
     * @return Collection of elements
     */
    OC splitCollection(final int partitionCount);

    /**
     * Provide total size of internal collection storing task results
     * @return Collection size
     */
    int internalCollectionSize();

}
