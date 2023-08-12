package com.engineersbox.exmesh.graph;

import com.engineersbox.exmesh.execution.Task;
import com.google.errorprone.annotations.DoNotCall;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import javax.annotation.security.DenyAll;
import java.util.function.Supplier;

public class Mesh<E extends Pipe> extends DirectedWeightedMultigraph<Task<?,?,?,?>, E> {

    private final PipeProvider pipeProvider;

    public Mesh(final Supplier<E> pipeProvider) {
        super(null, pipeProvider);
        this.pipeProvider = null;
    }

    public Mesh(final PipeProvider pipeProvider) {
        super(null, null);
        this.pipeProvider = pipeProvider;
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>DESTINATION</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param destinationVertex Destination task capable of consuming singletons of results
     * @return Edge instance
     * @param <DS> Destination singleton type
     * @param <SS> Source singleton type (must extend or be the same as destination singleton type)
     */
    @SuppressWarnings("UnusedReturnValue")
    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> destinationVertex) {
        return addEdgeS2S(
                sourceVertex,
                destinationVertex,
                this.pipeProvider != null
                        ? this.pipeProvider.provide(sourceVertex, destinationVertex)
                        : null
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>DESTINATION</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param destinationVertex Destination task capable of consuming singletons of results
     * @return Edge instance
     * @param <DS> Destination singleton type
     * @param <SS> Source singleton type (must extend or be the same as destination singleton type)
     */
    public <DS, SS extends DS> E addEdgeS2S(final Task<?,?,?,SS> sourceVertex,
                                            final Task<DS,?,?,?> destinationVertex,
                                            final E edge) {

        return saturateEdgeType(
                addEdge(
                        sourceVertex,
                        destinationVertex,
                        edge,
                        ConnectionType.SINGLETON_TO_SINGLETON
                ),
                ConnectionType.SINGLETON_TO_SINGLETON
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>DESTINATION</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param destinationVertex Destination task capable of consuming collections of results
     * @return Edge instance
     * @param <DC> Destination collection type
     * @param <SC> Source collection type (must extend or be the same as destination collection type)
     */
    @SuppressWarnings("UnusedReturnValue")
    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> destinationVertex) {
        return addEdgeC2C(
                sourceVertex,
                destinationVertex,
                this.pipeProvider != null
                        ? this.pipeProvider.provide(sourceVertex, destinationVertex)
                        : null
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>DESTINATION</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param destinationVertex Destination task capable of consuming collections of results
     * @return Edge instance
     * @param <DC> Destination collection type
     * @param <SC> Source collection type (must extend or be the same as destination collection type)
     */
    public <DC, SC extends DC> E addEdgeC2C(final Task<?,?,SC,?> sourceVertex,
                                            final Task<?,DC,?,?> destinationVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(
                        sourceVertex,
                        destinationVertex,
                        edge,
                        ConnectionType.COLLECTION_TO_COLLECTION
                ),
                ConnectionType.COLLECTION_TO_COLLECTION
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>DESTINATION</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param destinationVertex Destination task capable of consuming collections of results (from a singleton result)
     * @return Edge instance
     * @param <DC> Destination collection type
     * @param <SS> Source singleton type (must extend or be the same as destination collection type)
     */
    @SuppressWarnings("UnusedReturnValue")
    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> destinationVertex) {
        return addEdgeS2C(
                sourceVertex,
                destinationVertex,
                this.pipeProvider != null
                        ? this.pipeProvider.provide(sourceVertex, destinationVertex)
                        : null
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Singleton</b></li>
     *     <li><i>DESTINATION</i>: <b>Collection</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting singletons of results
     * @param destinationVertex Destination task capable of consuming collections of results (from a singleton result)
     * @return Edge instance
     * @param <DC> Destination collection type
     * @param <SS> Source singleton type (must extend or be the same as destination collection type)
     */
    public <DC, SS extends DC> E addEdgeS2C(final Task<?,?,?,SS> sourceVertex,
                                            final Task<?,DC,?,?> destinationVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(
                        sourceVertex,
                        destinationVertex,
                        edge,
                        ConnectionType.SINGLETON_TO_COLLECTION
                ),
                ConnectionType.SINGLETON_TO_COLLECTION
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>DESTINATION</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param destinationVertex Destination task capable of consuming singletons of results (from a collection of results)
     * @return Edge instance
     * @param <DS> Destination singleton type
     * @param <SC> Source collection type (must extend or be the same as destination singleton type)
     */
    @SuppressWarnings("UnusedReturnValue")
    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> destinationVertex) {
        return addEdgeC2S(
                sourceVertex,
                destinationVertex,
                this.pipeProvider != null
                        ? this.pipeProvider.provide(sourceVertex, destinationVertex)
                        : null
        );
    }

    /**
     * <p>
     * Add an edge between task vertices for the following configuration
     * <ul>
     *     <li><i>SOURCE</i>: <b>Collection</b></li>
     *     <li><i>DESTINATION</i>: <b>Singleton</b></li>
     * </ul>
     * </p>
     * @param sourceVertex Source task capable of emitting collections of results
     * @param destinationVertex Destination task capable of consuming singletons of results (from a collection of results)
     * @return Edge instance
     * @param <DS> Destination singleton type
     * @param <SC> Source collection type (must extend or be the same as destination singleton type)
     */
    public <DS, SC extends DS> E addEdgeC2S(final Task<?,?,SC,?> sourceVertex,
                                            final Task<DS,?,?,?> destinationVertex,
                                            final E edge) {
        return saturateEdgeType(
                addEdge(
                        sourceVertex,
                        destinationVertex,
                        edge,
                        ConnectionType.COLLECTION_TO_SINGLETON
                ),
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

    private void updateTaskParent(final Task<?,?,?,?> task) {
        if (task.getParent() == null) {
            task.setParent(this);
        }
    }

    private <S_IS, S_IC, S_OC, S_OS,
            D_IS, D_IC, D_OC, D_OS> E addEdge(final Task<S_IS, S_IC, S_OC, S_OS> sourceVertex,
                                              final Task<D_IS, D_IC, D_OC, D_OS> destinationVertex,
                                              final E edge,
                                              final ConnectionType connectionType) {
        if (sourceVertex == null) {
            throw new IllegalArgumentException("Source vertex cannot be null");
        } else if (destinationVertex == null) {
            throw new IllegalArgumentException("Destination vertex cannot be null");
        } else if (!destinationVertex.accepts(sourceVertex)) {
            throw new IllegalStateException(String.format(
                    "Cannot connect tasks [%s => %s] of connection kind [%s] with incompatible type bounds:\n\tSource Vertex:\n\t\t[OUT SINGLE: %s]\n\t\t[OUT COLLECTION: %s]\n\tDestination Vertex\n\t\t[IN SINGLE: %s]\n\t\t[IN COLLECTION: %s]",
                    sourceVertex.getName(), destinationVertex.getName(),
                    connectionType.name(),
                    sourceVertex.getOutputSingleType().getType(), sourceVertex.getOutputCollectionType().getType(),
                    destinationVertex.getInputSingletonType().getType(), destinationVertex.getInputCollectionType().getType()
            ));
        }
        updateTaskParent(sourceVertex);
        updateTaskParent(destinationVertex);
        if (edge != null) {
            return super.addEdge(sourceVertex, destinationVertex, edge) ? edge : null;
        }
        return super.addEdge(sourceVertex, destinationVertex);
    }

    /**
     * @deprecated Generic mesh edge creation is not supported. Use connection specific handlers.
     */
    @Deprecated
    @DoNotCall
    @DenyAll
    @Override
    public E addEdge(final Task<?, ?, ?, ?> sourceVertex,
                     final Task<?, ?, ?, ?> destinationVertex) {
        throw new UnsupportedOperationException("Generic mesh edge creation is not supported. Use connection specific handlers.");
    }

    /**
     * @deprecated Generic mesh edge creation is not supported. Use connection specific handlers.
     */
    @Deprecated
    @DoNotCall
    @DenyAll
    @Override
    public boolean addEdge(final Task<?, ?, ?, ?> sourceVertex,
                           final Task<?, ?, ?, ?> destinationVertex,
                           final E edge) {
        throw new UnsupportedOperationException("Generic mesh edge creation is not supported. Use connection specific handlers.");
    }
}
