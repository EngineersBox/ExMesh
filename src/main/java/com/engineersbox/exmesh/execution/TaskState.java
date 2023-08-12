package com.engineersbox.exmesh.execution;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public enum TaskState {
    CREATED,
    SCHEDULED,
    PENDING,
    ALLOCATED,
    EXECUTING,
    FINISHED;

    private static final List<TaskState> STATES = EnumUtils.getEnumList(TaskState.class);

    public TaskState transition() {
        final int ordinal = ordinal();
        if (ordinal >= STATES.size() - 1) {
            return FINISHED;
        }
        return STATES.get(ordinal);
    }
}
