package com.engineersbox.exmesh.util;

import java.util.Stack;

public class FixedSizeStack<E> extends Stack<E> {

    private final int capacity;

    public FixedSizeStack(final int capacity) {
        this.capacity = capacity;
        this.setSize(capacity);
    }

    @Override
    public synchronized E push(E item) {
        if (size() >= capacity) {
            return null;
        }
        return super.push(item);
    }
}
