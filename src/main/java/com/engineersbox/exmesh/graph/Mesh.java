package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;
import org.checkerframework.checker.units.qual.C;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.Set;
import java.util.function.Supplier;

public class Mesh<E extends Pipe> extends DirectedWeightedMultigraph<Task<?,?,?,?>, E> {


    public Mesh(final Supplier<E> edgeSupplier) {
        super(null, edgeSupplier);
    }

    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex) {
        return addEdgeS2S(sourceVertex, targetVertex, null);
    }

    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex,
                                            final E edge) {

        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, "single-to-single"),
                ConnectionType.S2S
        );
    }

    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex) {
        return addEdgeC2C(sourceVertex, targetVertex, null);
    }

    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, "collection-to-collection"),
                ConnectionType.C2C
        );
    }

    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex) {
        return addEdgeS2C(sourceVertex, targetVertex, null);
    }

    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, "single-to-collection"),
                ConnectionType.S2C
        );
    }

    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex) {
        return addEdgeC2S(sourceVertex, targetVertex, null);
    }

    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, "collection-to-single"),
                ConnectionType.C2S
        );
    }

    private E saturateEdgeType(final E edge,
                               final ConnectionType type) {
        if (edge != null) {
            edge.setConnectionType(type);
        }
        return edge;
    }

    private <S_IS, S_IC, S_OC, S_OS,
            D_IS, D_IC, D_OC, D_OS> E addEdge(final Task<S_IS, S_IC, S_OC, S_OS> sourceVertex,
                                              final Task<D_IS, D_IC, D_OC, D_OS> targetVertex,
                                              final E edge,
                                              final String invokedKind) {
        if (!targetVertex.accepts(sourceVertex)) {
            throw new IllegalStateException(String.format(
                    "Cannot connect tasks [%s => %s] of connection kind [%s] with incompatible type bounds:\n\tSource Vertex:\n\t\t[OUT SINGLE: %s]\n\t\t[OUT COLLECTION: %s]\n\tTarget Vertex\n\t\t[IN SINGLE: %s]\n\t\t[IN COLLECTION: %s]",
                    sourceVertex.getName(), targetVertex.getName(),
                    invokedKind,
                    sourceVertex.getOutputSingleType().getType(), sourceVertex.getOutputCollectionType().getType(),
                    targetVertex.getInputSingleType().getType(), targetVertex.getInputCollectionType().getType()
            ));
        } else if (edge != null) {
            return super.addEdge(sourceVertex, targetVertex, edge) ? edge : null;
        }
        return super.addEdge(sourceVertex, targetVertex);
    }

    @Deprecated
    @Override
    public E addEdge(final Task<?, ?, ?, ?> sourceVertex,
                     final Task<?, ?, ?, ?> targetVertex) {
        throw new UnsupportedOperationException("Generic direct graph edge creation is not supported. Use connection specific handlers.");
    }

    @Deprecated
    @Override
    public boolean addEdge(final Task<?, ?, ?, ?> sourceVertex,
                           final Task<?, ?, ?, ?> targetVertex,
                           final E edge) {
        throw new UnsupportedOperationException("Generic direct graph edge creation is not supported. Use connection specific handlers.");
    }
}
