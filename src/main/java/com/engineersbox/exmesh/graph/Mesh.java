package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.function.Supplier;

public class Mesh<E extends Pipe> extends DirectedWeightedMultigraph<Task<?,?,?,?>, E> {

    public Mesh(final Supplier<E> edgeSupplier) {
        super(null, edgeSupplier);
    }

    @Override
    public E addEdge(final Task<?,?,?,?> sourceVertex,
                     final Task<?,?,?,?> targetVertex) {
        if (!targetVertex.accepts(sourceVertex)) {
            throw new IllegalStateException(String.format(
                    "Cannot connect vertices with incompatible type bounds:\n\t[SINGLE | SINGLE] %s => %s\n\t[SINGLE | COLLECTION] %s => %s\n\t[COLLECTION | COLLECTION] %s => %s",
                    sourceVertex.getOutputSingleType(), targetVertex.getInputSingleType(),
                    sourceVertex.getOutputSingleType(), targetVertex.getOutputCollectionType(),
                    sourceVertex.getOutputCollectionType(), targetVertex.getOutputCollectionType()
            ));
        }
        return super.addEdge(sourceVertex, targetVertex);
    }

}
