package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

public class Mesh extends DirectedPseudograph<Task<?,?,?,?>, DefaultEdge> {

    public Mesh() {
        super(DefaultEdge.class);
    }

    @Override
    public DefaultEdge addEdge(final Task<?,?,?,?> sourceVertex,
                               final Task<?,?,?,?> targetVertex) {
        if (targetVertex.accepts(sourceVertex)) {
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
