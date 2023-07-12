package com.engineersbox.exmesh.execution.dependency;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.graph.Pipe;
import org.eclipse.collections.api.RichIterable;

/**
 * Method for implementing a given {@link AllocationStrategy} for a given set of pipes connected to a task.
 */
@FunctionalInterface
public interface AllocationCondition {

    boolean shouldAllocate(final RichIterable<Pipe> pipes,
                           final Task<?,?,?,?> task);

}
