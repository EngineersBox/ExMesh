package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.function.Supplier;

public class Mesh<E extends Pipe> extends DirectedWeightedMultigraph<Task<?,?,?,?>, E> {


    public Mesh(final Supplier<E> edgeSupplier) {
        super(null, edgeSupplier);
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>TARGET</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param targetVertex Target task capable of consuming singletons of results
     * @return Edge instance
     * @param <DS> Target singleton type
     * @param <SS> Source singleton type (must extend or be the same as target singleton type)
     */
    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex) {
        return addEdgeS2S(sourceVertex, targetVertex, null);
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>TARGET</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param targetVertex Target task capable of consuming singletons of results
     * @return Edge instance
     * @param <DS> Target singleton type
     * @param <SS> Source singleton type (must extend or be the same as target singleton type)
     */
    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex,
                                            final E edge) {

        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, ConnectionType.SINGLETON_TO_SINGLETON),
                ConnectionType.SINGLETON_TO_SINGLETON
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>TARGET</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param targetVertex Target task capable of consuming collections of results
     * @return Edge instance
     * @param <DC> Target collection type
     * @param <SC> Source collection type (must extend or be the same as target collection type)
     */
    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex) {
        return addEdgeC2C(sourceVertex, targetVertex, null);
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>TARGET</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param targetVertex Target task capable of consuming collections of results
     * @return Edge instance
     * @param <DC> Target collection type
     * @param <SC> Source collection type (must extend or be the same as target collection type)
     */
    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, ConnectionType.COLLECTION_TO_COLLECTION),
                ConnectionType.COLLECTION_TO_COLLECTION
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>TARGET</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param targetVertex Target task capable of consuming collections of results (from a singleton result)
     * @return Edge instance
     * @param <DC> Target collection type
     * @param <SS> Source singleton type (must extend or be the same as target collection type)
     */
    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex) {
        return addEdgeS2C(sourceVertex, targetVertex, null);
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>TARGET</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param targetVertex Target task capable of consuming collections of results (from a singleton result)
     * @return Edge instance
     * @param <DC> Target collection type
     * @param <SS> Source singleton type (must extend or be the same as target collection type)
     */
    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, ConnectionType.SINGLETON_TO_COLLECTION),
                ConnectionType.SINGLETON_TO_COLLECTION
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>TARGET</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param targetVertex Target task capable of consuming singletons of results (from a collection of results)
     * @return Edge instance
     * @param <DS> Target singleton type
     * @param <SC> Source collection type (must extend or be the same as target singleton type)
     */
    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex) {
        return addEdgeC2S(sourceVertex, targetVertex, null);
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>TARGET</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param targetVertex Target task capable of consuming singletons of results (from a collection of results)
     * @return Edge instance
     * @param <DS> Target singleton type
     * @param <SC> Source collection type (must extend or be the same as target singleton type)
     */
    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> targetVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(sourceVertex, targetVertex, edge, ConnectionType.COLLECTION_TO_SINGLETON),
                ConnectionType.COLLECTION_TO_SINGLETON
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
                                              final ConnectionType connectionType) {
        if (!targetVertex.accepts(sourceVertex)) {
            throw new IllegalStateException(String.format(
                    "Cannot connect tasks [%s => %s] of connection kind [%s] with incompatible type bounds:\n\tSource Vertex:\n\t\t[OUT SINGLE: %s]\n\t\t[OUT COLLECTION: %s]\n\tTarget Vertex\n\t\t[IN SINGLE: %s]\n\t\t[IN COLLECTION: %s]",
                    sourceVertex.getName(), targetVertex.getName(),
                    connectionType.name(),
                    sourceVertex.getOutputSingleType().getType(), sourceVertex.getOutputCollectionType().getType(),
                    targetVertex.getInputSingleType().getType(), targetVertex.getInputCollectionType().getType()
            ));
        } else if (edge != null) {
            return super.addEdge(sourceVertex, targetVertex, edge) ? edge : null;
        }
        return super.addEdge(sourceVertex, targetVertex);
    }

    /**
     * Generic mesh edge creation is not supported. Use connection specific handlers.
     */
    @Deprecated
    @Override
    public E addEdge(final Task<?, ?, ?, ?> sourceVertex,
                     final Task<?, ?, ?, ?> targetVertex) {
        throw new UnsupportedOperationException("Generic mesh edge creation is not supported. Use connection specific handlers.");
    }

    /**
     * Generic mesh edge creation is not supported. Use connection specific handlers.
     */
    @Deprecated
    @Override
    public boolean addEdge(final Task<?, ?, ?, ?> sourceVertex,
                           final Task<?, ?, ?, ?> targetVertex,
                           final E edge) {
        throw new UnsupportedOperationException("Generic mesh edge creation is not supported. Use connection specific handlers.");
    }
}
