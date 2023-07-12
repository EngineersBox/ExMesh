package com.engineersbox.exmesh.execution.policy;

/**
 * Actions a given {@link PipeInterfacingPolicy} over a set of connected pipes to retrieve result batches to be
 * handled by the current task connected to dependencies by the pipes.
 */
public class ResultBatchRetriever {

    private final PipeInterfacingPolicy policy;

    public ResultBatchRetriever(final PipeInterfacingPolicy policy) {
        this.policy = policy;
    }

}
