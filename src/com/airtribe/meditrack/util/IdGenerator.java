package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;


public final class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger(0);

    private IdGenerator() {
    }

    
    public static IdGenerator getInstance() {
        return Holder.INSTANCE;
    }

    
    public int generateId() {
        return counter.incrementAndGet();
    }

    
    public void ensureAtLeast(int min) {
        if (min <= 0) return;
        counter.updateAndGet(curr -> Math.max(curr, min));
    }

    
    private static class Holder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }
}
