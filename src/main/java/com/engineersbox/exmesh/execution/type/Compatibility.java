package com.engineersbox.exmesh.execution.type;

import com.google.common.reflect.TypeToken;

@FunctionalInterface
public interface Compatibility<T> {

    /**
     * Determine whether a given value of type (from) can be passed to a recipient expecting a value of type to
     * @param from Source value type
     * @param to Recipient expected type
     * @return Whether the types are compatible (true) or not (false)
     */
    boolean check(final TypeToken<?> from, final TypeToken<T> to);

    /**
     * Standard implementation of compatibility check, equality and then supertype bounds
     * @param <T> Recipient expected type
     */
    class Default<T> implements Compatibility<T> {

        @Override
        public boolean check(final TypeToken<?> from, final TypeToken<T> to) {
            return to.equals(from) || to.isSupertypeOf(from) || from.isSubtypeOf(to);
        }
    }

}
