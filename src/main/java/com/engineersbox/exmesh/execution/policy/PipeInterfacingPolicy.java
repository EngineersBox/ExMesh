package com.engineersbox.exmesh.execution.policy;

/**
 * <p>
 * Determines how a given task with connected pipes to dependent tasks, should retrieve result batches pushed into
 * deques within each connected pipe. Properties of connected pipes are visible to this policy to support context
 * dependent behaviour.
 * </p>
 * <p>
 * For example, a policy could be round-robin between all connected pipes.
 * </p>
 */
public abstract class PipeInterfacingPolicy {
}
