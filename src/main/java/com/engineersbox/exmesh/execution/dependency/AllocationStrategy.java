package com.engineersbox.exmesh.execution.dependency;

/**
 * Determines when a resource is allocated to a task.
 */
public enum AllocationStrategy {
    /**
     * Any connected pipe has at least one result in it
     */
    ANY_WITH_ONE,
    /**
     * All connected pipes have at least one result in them
     */
    ALL_WITH_ONE,
    /**
     * Any connected pipe with all results in it (deque)
     */
    ANY_WITH_ALL,
    /**
     * All connected pipes with all results in it (full deque)
     */
    ALL_WITH_ALL,
    /**
     * Provide a custom strategy for allocation
     */
    CUSTOM
}
