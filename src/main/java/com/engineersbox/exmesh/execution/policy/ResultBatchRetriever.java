package com.engineersbox.exmesh.execution.policy;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Pipe;
import org.eclipse.collections.api.factory.Sets;

import java.util.Set;

/**
 * Actions a given {@link PipeInterfacingPolicy} over a set of connected pipes to retrieve result batches to be
 * handled by the current task connected to dependencies by the pipes.
 */
public class ResultBatchRetriever extends PipeInterfacingPolicy {

    @Override
    @SuppressWarnings("unchecked")
    public <IS,IC,OC,OS> void interfaceWith(final Set<? extends Pipe> sourcePipes,
                                            final Task<IS,IC,OC,OS> task) {
        for (final Pipe pipe : sourcePipes) {
            switch (pipe.getConnectionType()) {
                case SINGLETON_TO_SINGLETON, COLLECTION_TO_SINGLETON -> pipe.drain(
                        (final Object obj) -> task.consolidateSingleton((Iterable<IS>) Sets.fixedSize.of(
                                task.getInputSingletonType().getRawType().cast(obj)
                        ))
                );
                case COLLECTION_TO_COLLECTION, SINGLETON_TO_COLLECTION -> pipe.drain(
                        (final Object obj) -> task.consolidateCollection((Iterable<IC>) Sets.fixedSize.of(
                                task.getInputCollectionType().getRawType().cast(obj)
                        ))
                );
            }
        }
    }

}
