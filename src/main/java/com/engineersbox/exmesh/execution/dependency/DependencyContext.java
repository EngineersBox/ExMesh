package com.engineersbox.exmesh.execution.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies how the resource allocation and scheduler should behave with regards to the dependency this task
 * has with previous tasks and the desired behavioural conditions around that.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DependencyContext {
    AllocationStrategy strategy();
    ExecutionCondition condition();
}
