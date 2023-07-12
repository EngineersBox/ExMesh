package com.engineersbox.exmesh.execution.dependency;

/**
 * Determines when a resource is allocated to a task.
 */
public enum AllocationStrategy {
    ANY_WITH_ONE, // Any connected pipe has at least one result in it
    ALL_WITH_ONE, // All connected pipes have at least one result in them
    ANY_WITH_ALL, // Any connected pipe with all results in it (deque)
    ALL_WITH_ALL, // All connected pipes with all results in it (full deque)
    CUSTOM // DIY!
}
