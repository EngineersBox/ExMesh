package com.engineersbox.exmesh.execution.type;

import javax.annotation.Nonnull;

public interface Consolidatable<IS, IC> {

    /**
     * Accept single values as an input from another task, accumulated with any internal collection type if necessary.
     * Implementation should not accept nulls, use {@link java.util.Optional} wrappers to indicate absent elements.
     * @param values Output(s) of a previous task (non-null)
     * @return Collection or singleton accumulated with input value
     */
    IC consolidateSingleton(@Nonnull final Iterable<IS> values);

    /**
     * Accept collections as an input from another task, accumulated with any internal collection tye if necessary.
     * Implementation should not accept nulls, use {@link java.util.Optional} wrappers to indicate absent elements.
     * @param collections Output(s) of a previous task (non-null)
     * @return Collection or singleton accumulated with input collection
     */
    IC consolidateCollection(@Nonnull final Iterable<IC> collections);

}
