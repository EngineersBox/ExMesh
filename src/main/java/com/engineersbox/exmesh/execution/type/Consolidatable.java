package com.engineersbox.exmesh.execution.type;

public interface Consolidatable<IS, IC> {

    /**
     * Accept single values as an input from another task, accumulated with any internal collection type if necessary
     * @param values Output(s) of a previous task
     * @return Collection or singleton accumulated with input value
     */
    IC consolidateSingleton(final Iterable<IS> values);

    /**
     * Accept collections as an input from another task, accumulated with any internal collection tye if necessary
     * @param collections Output(s) of a previous task
     * @return Collection or singleton accumulated with input collection
     */
    IC consolidateCollection(final Iterable<IC> collections);

}
