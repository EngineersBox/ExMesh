package com.engineersbox.exmesh.resource;

import com.engineersbox.exmesh.execution.ExecutionResult;
import com.engineersbox.exmesh.execution.Task;

import java.util.function.Function;

public interface AllocatableResource extends Function<Task<?,?,?,?>, ExecutionResult> {

    void configure();

    @Override
    ExecutionResult apply(final Task task);

}
