package com.engineersbox.exmesh.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Pipe<T> extends DefaultWeightedEdge implements Queue<T> {

    private final ConcurrentLinkedQueue<T> queue;
    private final int capacity;
    private final ReentrantLock lock;

    public Pipe(final int capacity) {
        this.queue = new ConcurrentLinkedQueue<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    @Override
    public synchronized boolean offer(final T t) {
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
    public Iterator<T> iterator() {
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
    public boolean add(final T t) {
        return offer(t);
    }

    @Override
    public boolean remove(final Object o) {
        return this.queue.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.queue.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        return this.queue.addAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.queue.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.queue.retainAll(c);
    }

    @Override
    public void clear() {
        this.queue.clear();
    }

    @Override
    public T remove() {
        return this.queue.remove();
    }

    @Override
    public T poll() {
        return this.queue.poll();
    }

    @Override
    public T element() {
        return this.queue.element();
    }

    @Override
    public T peek() {
        return this.queue.peek();
    }

}
