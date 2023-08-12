package com.engineersbox.exmesh;

import com.engineersbox.exmesh.execution.Task;
import com.engineersbox.exmesh.execution.dependency.AllocationStrategy;
import com.engineersbox.exmesh.execution.dependency.SchedulingBehaviour;
import com.engineersbox.exmesh.execution.dependency.ExecutionCondition;
import com.engineersbox.exmesh.graph.Mesh;
import com.engineersbox.exmesh.graph.Pipe;
import com.engineersbox.exmesh.scheduling.Scheduler;
import com.engineersbox.exmesh.scheduling.algorithm.WarpInterleavedScheduler;

import java.util.Collection;
import java.util.List;

public class Main {

    private static class TaskA extends Task<String, Void, List<Double>, Double> {

        public TaskA() {
            super("A", 1.0);
        }

        @Override
        public List<Double> invoke(Void input) {
            return null;
        }

        @Override
        public Void consolidateSingle(String... values) {
            return null;
        }

        @Override
        public Void consolidateCollection(Void... collections) {
            return null;
        }

        @Override
        public Double splitSingle() {
            return null;
        }

        @Override
        public List<Double> splitCollection(int count) {
            return null;
        }
    }

    private static class TaskB extends Task<List<Double>, List<Double>, Iterable<Integer>, Iterable<Integer>> {

        public TaskB() {
            super("B", 1.0);
        }

        @Override
        public Iterable<Integer> invoke(List<Double> input) {
            return null;
        }

        @Override
        public List<Double> consolidateSingle(List<Double>... values) {
            return null;
        }

        @Override
        public List<Double> consolidateCollection(List<Double>... collections) {
            return null;
        }

        @Override
        public Iterable<Integer> splitSingle() {
            return null;
        }

        @Override
        public Iterable<Integer> splitCollection(int count) {
            return null;
        }
    }

    private static class TaskC extends Task<Double, Iterable<Double>, Collection<Integer>, Collection<Integer>> {

        public TaskC() {
            super("C", 1.0);
        }

        @Override
        public Collection<Integer> invoke(Iterable<Double> input) {
            return null;
        }

        @Override
        public Collection<Double> consolidateSingle(Double... values) {
            return null;
        }

        @Override
        public Collection<Double> consolidateCollection(Iterable<Double>... collections) {
            return null;
        }

        @Override
        public Collection<Integer> splitSingle() {
            return null;
        }

        @Override
        public Collection<Integer> splitCollection(int count) {
            return null;
        }
    }

    @SchedulingBehaviour(
            strategy = AllocationStrategy.ANY_WITH_ONE,
            condition = ExecutionCondition.PIPELINED
    )
    private static class TaskD extends Task<Integer, Iterable<Integer>, Void, Void> {

        public TaskD() {
            super("D", 1.0);
        }

        @Override
        public Void invoke(Iterable<Integer> input) {
            return null;
        }

        @Override
        public Iterable<Integer> consolidateSingle(Integer... values) {
            return null;
        }

        @Override
        public Iterable<Integer> consolidateCollection(Iterable<Integer>... collections) {
            return null;
        }

        @Override
        public Void splitSingle() {
            return null;
        }

        @Override
        public Void splitCollection(int count) {
            return null;
        }
    }

    private static final class TestTask<IS,IC,OC,OS> extends Task<IS,IC,OC,OS> {

        public TestTask() {
            super("E", 1.0);
        }

        @Override
        public OC invoke(IC input) {
            return null;
        }

        @Override
        public IC consolidateSingle(IS... values) {
            return null;
        }

        @Override
        public IC consolidateCollection(IC... collections) {
            return null;
        }

        @Override
        public OS splitSingle() {
            return null;
        }

        @Override
        public OC splitCollection(int count) {
            return null;
        }
    }

    public static void main(final String[] args) {
        final Mesh<Pipe> mesh = new Mesh<>(() -> new Pipe(5));
        final TaskA A = new TaskA();
        final TaskB B = new TaskB();
        final TaskC C = new TaskC();
        final TaskD D = new TaskD();
//        final TestTask<String, Integer, Double, Float> E = new TestTask<>();
        mesh.addVertex(A);
        mesh.addVertex(B);
        mesh.addVertex(C);
        mesh.addVertex(D);
//        mesh.addVertex(E);
        mesh.addEdgeC2S(A, B);
        mesh.addEdgeS2S(A, C);
        mesh.addEdgeS2C(B, D);
        mesh.addEdgeC2C(C, D);
//        mesh.addEdgeC2C(C, new TestTask<String, Collection<Integer>,Void,Void>());

        final Scheduler warpInterleavedScheduler = new WarpInterleavedScheduler(
                4,
                2,
                10
        );
        warpInterleavedScheduler.submit(mesh);
    }

}
