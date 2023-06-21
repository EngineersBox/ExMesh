package com.engineersbox.exmesh.graph;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Pipe<T> extends ConcurrentLinkedQueue<T> {

    private final int capacity;
    private final ReentrantLock lock;

    public Pipe(final int capacity) {
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    @Override
    public synchronized boolean offer(final T t) {
        lock.lock();
        try {
            if (size() >= capacity) {
                return false;
            }
            return super.offer(t);
        } finally {
            lock.unlock();
        }
    }
}
