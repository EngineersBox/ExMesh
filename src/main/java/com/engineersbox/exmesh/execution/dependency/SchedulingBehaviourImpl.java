package com.engineersbox.exmesh.execution.dependency;

import java.lang.annotation.Annotation;

public class SchedulingBehaviourImpl implements SchedulingBehaviour {

    private final AllocationStrategy strategy;
    private final ExecutionCondition condition;

    public SchedulingBehaviourImpl(final AllocationStrategy strategy,
                                   final ExecutionCondition condition) {
        this.strategy = strategy;
        this.condition = condition;
    }

    public static SchedulingBehaviour ofDefault() {
        return new SchedulingBehaviourImpl(
                AllocationStrategy.ANY_WITH_ONE,
                ExecutionCondition.PIPELINED
        );
    }

    @Override
    public AllocationStrategy strategy() {
        return this.strategy;
    }

    @Override
    public ExecutionCondition condition() {
        return this.condition;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return SchedulingBehaviour.class;
    }
}
