package com.engineersbox.exmesh;

import com.engineersbox.exmesh.execution.Task;
import org.eclipse.collections.api.RichIterable;

import java.util.Collection;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        new Task<String, RichIterable<Integer>, Collection<Double>, Map<String, Float>>("Test Task", 2.4){

            @Override
            public Collection<Double> invoke(RichIterable<Integer> input) {
                return null;
            }

            @Override
            public RichIterable<Integer> consolidateSingle(String... values) {
                return null;
            }

            @Override
            public RichIterable<Integer> consolidateCollection(RichIterable<Integer>... collections) {
                return null;
            }

            @Override
            public Map<String, Float> splitSingle() {
                return null;
            }

            @Override
            public Collection<Double> splitCollection(int count) {
                return null;
            }
        };
    }

}
