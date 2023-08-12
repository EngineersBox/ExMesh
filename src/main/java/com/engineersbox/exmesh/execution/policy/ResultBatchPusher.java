package com.engineersbox.exmesh.execution.policy;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Pipe;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Set;

public class ResultBatchPusher extends PipeInterfacingPolicy {
    @Override
    public <IS, IC, OC, OS> void interfaceWith(final Set<? extends Pipe> pipes,
                                               final Task<IS, IC, OC, OS> task) {
        final MutableInt singletonCount = new MutableInt(0);
        final MutableInt collectionCount = new MutableInt(0);
        pipes.forEach((final Pipe pipe) -> {
            switch (pipe.getConnectionType()) {
                case SINGLETON_TO_SINGLETON, SINGLETON_TO_COLLECTION -> singletonCount.increment();
                case COLLECTION_TO_COLLECTION, COLLECTION_TO_SINGLETON -> collectionCount.increment();
            }
        });
        final int collectionPartitionSize = collectionCount.getValue() < 1 ? 1 : (task.internalCollectionSize() - singletonCount.getValue()) / collectionCount.getValue();
        for (final Pipe pipe : pipes) {
            switch (pipe.getConnectionType()) {
                case SINGLETON_TO_SINGLETON, SINGLETON_TO_COLLECTION -> pipe.offer(task.splitSingle());
                case COLLECTION_TO_COLLECTION, COLLECTION_TO_SINGLETON -> pipe.offer(task.splitCollection(collectionPartitionSize));
            }
        }
    }
}
