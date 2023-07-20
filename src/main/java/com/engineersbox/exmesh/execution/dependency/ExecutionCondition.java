package com.engineersbox.exmesh.execution.dependency;

/**
 * Determines the conditions for task execution in regards to dependent results from previous stages, assuming
 * a resource has been allocated.
 */
public enum ExecutionCondition {
    /**
     * Wait for all previous tasks to finish
     */
    BARRIER,
    /**
     * Start immediately on queued results, if resources have benn allocated
     */
    PIPELINED
}
