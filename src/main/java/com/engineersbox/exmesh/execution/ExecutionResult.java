package com.engineersbox.exmesh.execution;

public class ExecutionResult {

    private State state;

    public void setState(final State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public enum State {
        PENDING,
        RUNNING,
        SUCCEEDED,
        FAILED,
        ABORTED;

        public boolean isFinished() {
            return ordinal() > 1;
        }
    }

}
