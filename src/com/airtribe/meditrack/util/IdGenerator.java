package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe, lazily-initialized singleton that generates unique integer IDs.
 *
 * <p>Implemented using the Initialization-on-demand holder idiom which provides
 * lazy initialization, thread-safety, and prevents multiple instances without
 * explicit synchronization.</p>
 */
public final class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger(0);

    private IdGenerator() {
    }

    /**
     * Returns the singleton instance of {@code IdGenerator}.
     *
     * @return the singleton instance
     */
    public static IdGenerator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Generates and returns a new unique id. IDs start from {@code 1} and
     * increment by one for each invocation. This method is thread-safe.
     *
     * @return a newly generated unique id
     */
    public int generateId() {
        return counter.incrementAndGet();
    }

    /**
     * Ensures the internal counter is at least {@code min}. This is useful when
     * bootstrapping the generator from persisted data so subsequently generated
     * ids do not collide with existing ones.
     *
     * @param min minimal value the counter should have (non-negative)
     */
    public void ensureAtLeast(int min) {
        if (min <= 0) return;
        counter.updateAndGet(curr -> Math.max(curr, min));
    }

    /**
     * Holder class for lazy-loaded singleton instance.
     */
    private static class Holder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }
}
