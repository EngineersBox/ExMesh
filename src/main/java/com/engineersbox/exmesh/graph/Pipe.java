package com.engineersbox.exmesh.graph;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Pipe extends DefaultWeightedEdge implements Queue<Object> {

    private final ConcurrentLinkedQueue<Object> queue;
    private final int capacity;
    private final ReentrantLock lock;

    public Pipe(final int capacity) {
        this.queue = new ConcurrentLinkedQueue<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    @Override
    public synchronized boolean offer(final Object t) {
        lock.lock();
        try {
            if (this.queue.size() >= capacity) {
                return false;
            }
            return this.queue.offer(t);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return this.queue.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return this.queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.queue.toArray();
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        return this.queue.toArray(a);
    }

    @Override
    public boolean add(final Object t) {
        return offer(t);
    }

    @Override
    public boolean remove(final Object o) {
        return this.queue.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull final Collection<?> c) {
        return this.queue.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull final Collection<?> c) {
        return this.queue.addAll(c);
    }

    @Override
    public boolean removeAll(@NonNull final Collection<?> c) {
        return this.queue.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull final Collection<?> c) {
        return this.queue.retainAll(c);
    }

    @Override
    public void clear() {
        this.queue.clear();
    }

    @Override
    public Object remove() {
        return this.queue.remove();
    }

    @Override
    public Object poll() {
        return this.queue.poll();
    }

    @Override
    public Object element() {
        return this.queue.element();
    }

    @Override
    public Object peek() {
        return this.queue.peek();
    }

}
