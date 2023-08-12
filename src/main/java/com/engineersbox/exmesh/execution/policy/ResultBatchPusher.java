package com.engineersbox.exmesh.execution.policy;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Pipe;

import java.util.Set;

public class ResultBatchPusher extends PipeInterfacingPolicy {
    @Override
    public <IS, IC, OC, OS> void interfaceWith(Set<? extends Pipe> pipes, Task<IS, IC, OC, OS> task) {
        final int size = task.internalCollectionSize();
        final int each = size / pipes.size();
        for (final Pipe pipe : pipes) {
            switch (pipe.getConnectionType()) {
                case SINGLETON_TO_SINGLETON, SINGLETON_TO_COLLECTION -> pipe.offer(task.splitSingle());
                case COLLECTION_TO_COLLECTION, COLLECTION_TO_SINGLETON -> pipe.fill(() -> task.splitCollection(each));
            }
        }
    }
}
