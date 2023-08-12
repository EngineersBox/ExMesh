package com.engineersbox.exmesh.execution.policy;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Pipe;

import java.util.Set;

/**
 * <p>
 * Determines how a given task with connected pipes to dependent tasks, should retrieve/push result batches from/to
 * deques within each connected pipe. Properties of connected pipes are visible to this policy to support context
 * dependent behaviour.
 * </p>
 * <p>
 * For example, a policy could be round-robin between all connected pipes.
 * </p>
 */
public abstract class PipeInterfacingPolicy {

    public abstract <IS,IC,OC,OS> void interfaceWith(final Set<? extends Pipe> pipes,
                                                     final Task<IS,IC,OC,OS> task);

}
