package com.engineersbox.exmesh.execution.type;

import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface Compatibility<T> {

    /**
     * Determine whether a given value of type (from) can be passed to a recipient expecting a value of type to
     * @param from Source value type
     * @param to Recipient expected type
     * @return Whether the types are compatible (true) or not (false)
     */
    boolean check(final TypeToken<?> from, final TypeToken<T> to);

    /**
     * Standard implementation of compatibility check, equality and then supertype bounds
     * @param <T> Recipient expected type
     */
    class Default<T> implements Compatibility<T> {

        private static final Logger LOGGER = LoggerFactory.getLogger(Default.class);
        private static final String CONDITIONS_MESSAGE = "\n\t1. A == B{}\n\t2. B supertype of A{}\n\t3. A subtype of B{}";
        private static final String FAIL_MATCH_LOG_MESSAGE = "[Fail Count: {}/{}] Incompatible types [A: {}] and [B: {}], at least one condition is required to match:" + CONDITIONS_MESSAGE;

        private final MutableInt failCount;
        private final int threshold;
        private final boolean warnNonFatal;

        public Default(final MutableInt failCount,
                       final int threshold,
                       final boolean warnNonFatal) {
            this.failCount = failCount;
            this.threshold = threshold;
            this.warnNonFatal = warnNonFatal;
        }

        @Override
        public boolean check(final TypeToken<?> from, final TypeToken<T> to) {
            LOGGER.debug("Checking compatibility between {} and {}", from, to);
            boolean equals;
            Boolean supertype = null;
            Boolean subtype = null;
            if ((equals = to.equals(from)) || (supertype = to.isSupertypeOf(from)) || (subtype = from.isSubtypeOf(to))) {
                LOGGER.trace(
                        "Conditions satisfied:" + CONDITIONS_MESSAGE,
                        ": " + equals,
                        ": " + (supertype == null ? "unknown" : supertype),
                        ": " + (subtype == null ? "unknown" : subtype)
                );
                return true;
            }
            final int nextFailedCount = this.failCount.incrementAndGet();
            if (nextFailedCount < this.threshold && this.warnNonFatal) {
                LOGGER.warn(FAIL_MATCH_LOG_MESSAGE, nextFailedCount, this.threshold, from, to, "", "", "");
            } else {
                LOGGER.error(FAIL_MATCH_LOG_MESSAGE, nextFailedCount, this.threshold, from, to, "", "", "");
            }
            return false;
        }
    }

}
