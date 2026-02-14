package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class DataStore<T> {

    private final List<T> items = new ArrayList<>();

    
    public DataStore() {
    }

    public synchronized void add(T item) {
        if (item == null) {
            throw new InvalidDataException("Item to add must not be null");
        }
        items.add(item);
    }

    public synchronized boolean remove(T item) {
        return items.remove(item);
    }

    
    public synchronized List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public synchronized Optional<T> findById(Function<T, Integer> idExtractor, int id) {
        Objects.requireNonNull(idExtractor, "idExtractor must not be null");

        for (T item : items) {
            try {
                Integer itemId = idExtractor.apply(item);
                if (itemId != null && itemId == id) {
                    return Optional.of(item);
                }
            } catch (RuntimeException ex) {
                // If extraction fails for a particular item, skip it.
            }
        }
        return Optional.empty();
    }
}
