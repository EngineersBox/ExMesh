package com.engineersbox.exmesh.execution.dependency;

import java.lang.annotation.*;

/**
 * Specifies how the resource allocation and scheduler should behave with regards to the dependency this task
 * has with previous tasks and the desired behavioural conditions around that.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SchedulingBehaviour {
    AllocationStrategy strategy();
    ExecutionCondition condition();

}
