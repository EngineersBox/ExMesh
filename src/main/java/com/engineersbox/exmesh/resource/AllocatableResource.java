package com.engineersbox.exmesh.resource;

import com.engineersbox.exmesh.execution.ExecutionResult;
import com.engineersbox.exmesh.execution.Task;

import javax.annotation.Nonnull;
import java.util.concurrent.Future;
import java.util.function.Function;

public interface AllocatableResource extends Function<Task<?,?,?,?>, Future<ExecutionResult>> {

    /**
     * Invoked during initial allocation
     */
    void startup();

    /**
     * Invoked before task is to be run on it
     */
    void configure();

    /**
     * Handler to perform a given task on the resource
     * @param task the task to be executed
     * @return result of the execution of the task
     */
    @Override
    @Nonnull
    Future<ExecutionResult> apply(final Task<?,?,?,?> task);

    /**
     * Invoked after task has been run on it
     */
    void cleanup();

    /**
     * Invoked during deallocation from resource pool
     */
    void teardown();

}
