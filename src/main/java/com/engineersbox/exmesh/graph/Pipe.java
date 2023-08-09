package com.engineersbox.exmesh.graph;

import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.atomic.SpscAtomicArrayQueue;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Pipe extends DefaultWeightedEdge implements MessagePassingQueue<Object> {

    private final SpscAtomicArrayQueue<Object> queue;
    private ConnectionType connectionType;

    public Pipe(final int capacity) {
        this(capacity, null);
    }

    public Pipe(final int capacity,
                final ConnectionType connectionType) {
        this.queue = new SpscAtomicArrayQueue<>(capacity);
        this.connectionType = connectionType;
    }

    public void setConnectionType(final ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public ConnectionType getConnectionType() {
        return this.connectionType;
    }

    @Override
    public boolean offer(final Object o) {
        return this.queue.offer(o);
    }

    @Override
    public Object poll() {
        return this.queue.poll();
    }

    @Override
    public Object peek() {
        return this.queue.peek();
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public void clear() {
        this.queue.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public int capacity() {
        return this.queue.capacity();
    }

    @Override
    public boolean relaxedOffer(final Object o) {
        return this.queue.relaxedOffer(o);
    }

    @Override
    public Object relaxedPoll() {
        return this.queue.relaxedPoll();
    }

    @Override
    public Object relaxedPeek() {
        return this.queue.relaxedPeek();
    }

    @Override
    public int drain(final Consumer<Object> consumer, final int limit) {
        return this.queue.drain(consumer, limit);
    }

    @Override
    public int fill(final Supplier<Object> supplier, final int limit) {
        return this.queue.fill(supplier, limit);
    }

    @Override
    public int drain(final Consumer<Object> consumer) {
        return this.queue.drain(consumer);
    }

    @Override
    public int fill(final Supplier<Object> supplier) {
        return this.queue.fill(supplier);
    }

    @Override
    public void drain(final Consumer<Object> consumer,
                      final WaitStrategy waitStrategy,
                      final ExitCondition exitCondition) {
        this.queue.drain(consumer, waitStrategy, exitCondition);
    }

    @Override
    public void fill(final Supplier<Object> supplier,
                     final WaitStrategy waitStrategy,
                     final ExitCondition exitCondition) {
        this.queue.fill(supplier, waitStrategy, exitCondition);
    }
}