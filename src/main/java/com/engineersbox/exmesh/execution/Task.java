package com.engineersbox.exmesh.execution;

import com.engineersbox.exmesh.execution.type.Consolidatable;
import com.engineersbox.exmesh.execution.type.Splittable;
import com.google.common.reflect.TypeToken;


public abstract class Task<IS, IC, OC, OS> implements Splittable<OS, OC>, Consolidatable<IS, IC> {

    private final TypeToken<IS> inputSingleType;
    private final TypeToken<IC> inputCollectionType;
    private final TypeToken<OS> outputSingleType;
    private final TypeToken<OC> outputCollectionType;

    {
        this.inputSingleType = new TypeToken<IS>(getClass()){};
        this.inputCollectionType = new TypeToken<IC>(getClass()){};
        this.outputSingleType = new TypeToken<OS>(getClass()){};
        this.outputCollectionType = new TypeToken<OC>(getClass()){};
    }

    private static boolean compatible(final TypeToken<?> source,
                                      final TypeToken<?> target) {
        return source.equals(target) || source.isSupertypeOf(target);
    }

    public boolean accepts(final TypeToken<?> type) {
        return Task.compatible(this.inputSingleType, type)
            || Task.compatible(this.inputCollectionType, type);
    }

    public boolean accepts(final Task<?,?,?,?> task) {
        return accepts(task.outputSingleType) || accepts(task.outputCollectionType);
    }

    public boolean provides(final TypeToken<?> type) {
        return Task.compatible(type, outputSingleType)
            || Task.compatible(type, outputCollectionType);
    }

    public boolean provides(final Task<?,?,?,?> task) {
        return provides(task.inputSingleType) || provides(task.inputCollectionType);
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
