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
                    "Cannot connect tasks [%s => %s] with incompatible type bounds:\n\tSource Vertex:\n\t\t[OUT SINGLE: %s]\n\t\t[OUT COLLECTION: %s]\n\tTarget Vertex\n\t\t[IN SINGLE: %s]\n\t\t[IN COLLECTION: %s]",
                    sourceVertex.getName(), targetVertex.getName(),
                    sourceVertex.getOutputSingleType().getType(), sourceVertex.getOutputCollectionType().getType(),
                    targetVertex.getInputSingleType().getType(), targetVertex.getInputCollectionType().getType()
            ));
        }
        return super.addEdge(sourceVertex, targetVertex);
    }

}
