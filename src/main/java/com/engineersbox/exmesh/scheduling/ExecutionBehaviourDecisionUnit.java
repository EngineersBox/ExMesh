package com.engineersbox.exmesh.scheduling;

import com.engineersbox.exmesh.util.FixedSizeStack;
import org.apache.commons.lang3.BitField;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.Sets;

public class ExecutionBehaviourDecisionUnit {

    private final FixedSizeStack<CCAEntry> ctaStack;
    private final MutableSetMultimap<Object, CCAEntry> nodeEntries;
    private final BitField nodeMask;
    private final BitField nodeAssistMask;
    private final int nodeCount;
    private final int containerCount;

    public ExecutionBehaviourDecisionUnit(final int nodeCount,
                                          final int containerCount,
                                          final int stackSize) {
        this.ctaStack = new FixedSizeStack<>(stackSize);
        this.nodeEntries = Multimaps.mutable.set.of();
        this.nodeMask = new BitField(nodeCount);
        this.nodeAssistMask = new BitField(nodeCount);
        this.nodeCount = nodeCount;
        this.containerCount = containerCount;
    }

    public synchronized BitField issue(final Object nodeRef) {
        final MutableSet<CCAEntry> nodeEntries = this.nodeEntries.getIfAbsentPutAll(nodeRef, Sets.mutable.empty());
        return null;
    }

    public void update() {

    }

    public void flag() {

    }

    /* Cooperative Container Array */
    public static class CCAEntry {

        private final Object nodeRef; // TODO: Need a way to map to parent node
        private final BitField containerMask;
        private final int containerCount;
        private int status;
        private boolean valid;

        public CCAEntry(final Object nodeRef,
                        final int containerCount) {
            this.nodeRef = nodeRef;
            this.containerMask = new BitField(containerCount);
            this.containerCount = containerCount;
            this.status = 0;
            this.valid = true;
        }

    }

}
