package com.engineersbox.exmesh.execution;

import com.engineersbox.exmesh.execution.type.Compatibility;
import com.engineersbox.exmesh.execution.type.Consolidatable;
import com.engineersbox.exmesh.execution.type.Splittable;
import com.google.common.reflect.TypeToken;

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
public abstract class Task<IS, IC, OC, OS> implements Splittable<OS, OC>, Consolidatable<IS, IC> {

    private final TypeToken<IS> inputSingleType;
    private final TypeToken<IC> inputCollectionType;
    private final TypeToken<OC> outputCollectionType;
    private final TypeToken<OS> outputSingleType;
    private Compatibility<IS> inputSingleCompat;
    private Compatibility<IC> inputCollectionCompat;

    {
        this.inputSingleType = new TypeToken<IS>(getClass()){};
        this.inputCollectionType = new TypeToken<IC>(getClass()){};
        this.outputCollectionType = new TypeToken<OC>(getClass()){};
        this.outputSingleType = new TypeToken<OS>(getClass()){};
        this.inputSingleCompat = new Compatibility.Default<>();
        this.inputCollectionCompat = new Compatibility.Default<>();
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

}
