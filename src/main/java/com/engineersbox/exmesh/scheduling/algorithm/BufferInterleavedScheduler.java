package com.engineersbox.exmesh.scheduling.algorithm;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.Scheduler;
import com.engineersbox.exmesh.scheduling.allocation.Allocator;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.stream.IntStream;

public class BufferInterleavedScheduler extends Scheduler {

    private final ImmutableList<ArrayDeque<Task<?,?,?,?>>> buffers;
    private final int bufferCount;
    private final int bufferSize;

    protected BufferInterleavedScheduler(final Allocator allocator,
                                         final int bufferCount,
                                         final int bufferSize) {
        super(allocator);
        this.buffers = Lists.immutable.fromStream(
                IntStream.range(0, bufferCount)
                        .mapToObj(_ignored -> new ArrayDeque<>(bufferSize))
        );
        this.bufferCount = bufferCount;
        this.bufferSize = bufferSize;
    }

    @Override
    public void submit(final Collection<Task<?, ?, ?, ?>> tasks) {

    }

    @Override
    public void submit(final Mesh<? extends Pipe> mesh) {

    }

    @Override
    public void analyse() {

    }

    @Override
    public void mark() {

    }

    @Override
    public void issue() {

    }
}
