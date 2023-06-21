package com.engineersbox.exmesh.execution.type;

import com.google.common.reflect.TypeToken;

@FunctionalInterface
public interface Compatibility<T> {

    boolean check(final TypeToken<?> from, final TypeToken<T> to);

    class Default<T> implements Compatibility<T> {

        @Override
        public boolean check(final TypeToken<?> from, final TypeToken<T> to) {
            return to.equals(from) || to.isSupertypeOf(from);
        }
    }

}
