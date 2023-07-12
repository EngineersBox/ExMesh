package com.engineersbox.exmesh.execution;

import com.engineersbox.exmesh.execution.type.Compatibility;
import com.engineersbox.exmesh.execution.type.Consolidatable;
import com.engineersbox.exmesh.execution.type.Splittable;
import com.google.common.collect.Streams;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Optional;

/**
 * <p>
 * Provides a set of standard interfaces for accepting inputs, processing them as a task and forwarding the results
 * from/to other tasks in the mesh.
 * </p>
 * <p>
 * Conceptually, a model of a one/many to one/many interface between nodes, where results from previous nodes are either
 * singleton types or collections of singletons in a specified format. Similarly, outputs to other tasks are in singleton
 * or collection or singleton format (as desired).
 * </p>
 *
 * @param <IS> Input singleton type
 * @param <IC> Input collection type (comprised of one or more singletons)
 * @param <OC> Output singleton type
 * @param <OS> Output collection type (comprised of one or more singletons)
 */
@SuppressWarnings("unchecked")
public abstract class Task<IS, IC, OC, OS> implements Splittable<OS, OC>, Consolidatable<IS, IC> {

    /* NOTE: A task that is waiting for one or more dependent tasks to complete, with results pushed into
     * deques in each edge (Pipe), can be allocated a thread in a pre-execution state (name TBC), which can
     * invoke the methods in the Consolidatable interface to collect results ahead of time to prevent waiting
     * for consolidation to happen at task start up.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    private static final String GENERIC_TASK_IMPLEMENTATION_ERROR_TEMPLATE = """
        Task implementation %s has unbounded generic parameter %s with upper-bound of %s. Refactor this task to ensure generic parameter is not erased. For example:
        A concrete implementation instead of generic instantiation, instead of the following:
        \tclass TestTask<IS,IC,OC,OS> extends Task<IS,IC,OC,OS> {...}
        \tnew TestTask<A,B,C,D>(...);
        Implement instead as follows:
        \tclass TestTask extends Task<A,B,C,D> { /* generic overrides */ }
        \tnew TestTask<>(...);
        Or alternatively:
        \tclass TestTask<IS,IC,OC,OS> extends Task<IS,IC,OC,OS> { /* concrete or forwarded generic overrides */ }
        \tnew TestTask<A,B,C,D>(...){ /* generic overrides */ }'
        Or potentially:
        \tnew Task<A,B,C,D>(...){ /* generic overrides */ }
    """;
    private static final String[] GENERIC_TYPE_NAMES;
    static {
        GENERIC_TYPE_NAMES = Arrays.stream(Task.class.getTypeParameters())
                .map(TypeVariable::getTypeName)
                .toArray(String[]::new);
    }

    private final TypeToken<IS> inputSingleType;
    private final TypeToken<IC> inputCollectionType;
    private final TypeToken<OC> outputCollectionType;
    private final TypeToken<OS> outputSingleType;
    private Compatibility<IS> inputSingleCompat;
    private Compatibility<IC> inputCollectionCompat;
    private final String name;
    private final double weight;

    protected Task(final String name,
                   final double weight) {
        this.name = name;
        this.weight = weight;
        this.inputSingleType = new TypeToken<IS>(getClass()){};
        this.inputCollectionType = new TypeToken<IC>(getClass()){};
        this.outputCollectionType = new TypeToken<OC>(getClass()){};
        this.outputSingleType = new TypeToken<OS>(getClass()){};
        final MutableInt failCount = new MutableInt();
        this.inputSingleCompat = new Compatibility.Default<>(failCount, 4, true);
        this.inputCollectionCompat = new Compatibility.Default<>(failCount, 4, true);
        checkImplementationConcrete();
        LOGGER.trace(
                "Created task {} | Weight {} | Input types [SINGLE: {}] [COLLECTION: {}] | Output types [SINGLE: {}] [COLLECTION: {}]",
                name, weight,
                this.inputSingleType,
                this.inputCollectionType,
                this.outputSingleType,
                this.outputCollectionType
        );
    }

    private void checkImplementationConcrete() {
        Class<?> clazz;
        for (clazz = getClass(); !clazz.getSuperclass().equals(Task.class); clazz = clazz.getSuperclass());
        final Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
        final Optional<Pair<Type, Type>> typeBound = Streams.zip(
                        Arrays.stream(GENERIC_TYPE_NAMES),
                        Arrays.stream(types),
                        Pair::of
                ).filter((final Pair<String, Type> names) -> names.getLeft().equals(names.getRight().getTypeName()))
                .map((final Pair<String, Type> names) -> {
                    final Type type = names.getRight();
                    if (type instanceof TypeVariable<?> typeVariable) {
                        final AnnotatedType[] bounds = typeVariable.getAnnotatedBounds();
                        return Pair.of(type, bounds[0].getType());
                    }
                    return Pair.of(type, (Type) Object.class);
                }).findFirst();
        typeBound.ifPresent((final Pair<Type, Type> bound) -> {
            throw new IllegalStateException(String.format(
                    GENERIC_TASK_IMPLEMENTATION_ERROR_TEMPLATE,
                    this.name,
                    bound.getLeft(),
                    bound.getRight()
            ));
        });
    }

    /**
     * Provide a compatibility interface to determine whether a given type is compatible with the type used as an
     * input singleton for this task.
     * @param inputSingleCompat Compatibility expression
     */
    public void withInputSingleCompatibility(final Compatibility<IS> inputSingleCompat) {
        this.inputSingleCompat = inputSingleCompat;
    }

    /**
     * Provide a compatibility interface to determine whether a given type is compatible with the type used as an
     * input collection for this task.
     * @param inputCollectionCompat Compatibility expression
     */
    public void withInputCollectionCompatibility(final Compatibility<IC> inputCollectionCompat) {
        this.inputCollectionCompat = inputCollectionCompat;
    }

    public boolean accepts(final TypeToken<?> type) {
        return this.inputSingleCompat.check(type, this.inputSingleType)
            || this.inputCollectionCompat.check(type, this.inputCollectionType);
    }

    public boolean accepts(final Task<?,?,?,?> task) {
        return accepts(task.outputSingleType) || accepts(task.outputCollectionType);
    }

    public TypeToken<IS> getInputSingleType() {
        return this.inputSingleType;
    }

    public TypeToken<IC> getInputCollectionType() {
        return this.inputCollectionType;
    }

    public TypeToken<OS> getOutputSingleType() {
        return this.outputSingleType;
    }

    public TypeToken<OC> getOutputCollectionType() {
        return this.outputCollectionType;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    /**
     * Perform the actual task on the consolidated inputs into an output collection or singleton
     * @param input Consolidated inputs from previous task(s)
     * @return Singleton or collection of output(s)
     */
    public abstract OC invoke(final IC input);

    /**
     * @see Consolidatable#consolidateSingle(Object[])
     */
    @Override
    public abstract IC consolidateSingle(final IS... values);

    /**
     * @see Consolidatable#consolidateCollection(Object[])
     */
    @Override
    public abstract IC consolidateCollection(final IC... collections);

    /**
     * @see Splittable#splitSingle()
     */
    @Override
    public abstract OS splitSingle();

    /**
     * @see Splittable#splitCollection(int)
     */
    @Override
    public abstract OC splitCollection(int count);

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
