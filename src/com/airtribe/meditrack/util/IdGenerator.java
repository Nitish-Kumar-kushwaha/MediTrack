package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe, lazily-initialized singleton for generating unique integer IDs.
 *
 * Uses the Initialization-on-demand holder idiom to ensure lazy initialization
 * and thread-safety without synchronization overhead on {@code getInstance()}.
 */
public final class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger();

    private IdGenerator() {
    }

    /**
     * Returns the singleton instance of {@code IdGenerator}.
     *
     * @return singleton instance
     */
    public static IdGenerator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Generates and returns a new unique id.
     *
     * @return new unique id (incrementing integer)
     */
    public int generateId() {
        return counter.incrementAndGet();
    }

    /**
     * Holder class for lazy-loaded singleton instance.
     */
    private static class Holder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }
}
