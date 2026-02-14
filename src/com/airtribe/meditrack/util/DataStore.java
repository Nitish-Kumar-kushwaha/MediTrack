package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Simple, generic in-memory data store.
 *
 * Responsibilities:
 * - Hold items of type {@code T}
 * - Provide basic query and mutation operations
 *
 * This class follows immutability/encapsulation best practices by not exposing
 * internal mutable state directly (returns snapshots / unmodifiable lists).
 *
 * @param <T> element type
 */
public class DataStore<T> {

    private final List<T> items = new ArrayList<>();

    /**
     * Adds an item to the store.
     *
     * @param item the item to add; must not be null
     * @throws NullPointerException if {@code item} is null
     */
    public void add(T item) {
        Objects.requireNonNull(item, "item must not be null");
        items.add(item);
    }

    /**
     * Removes an item from the store.
     *
     * @param item the item to remove
     * @return {@code true} if the item was present and removed, {@code false} otherwise
     */
    public boolean remove(T item) {
        return items.remove(item);
    }

    /**
     * Returns all items currently in the store as an unmodifiable list snapshot.
     *
     * @return unmodifiable list of all items
     */
    public List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    /**
     * Finds items that match the given predicate.
     *
     * @param predicate the predicate to match; must not be null
     * @return unmodifiable list of matching items (empty if none)
     * @throws NullPointerException if {@code predicate} is null
     */
    public List<T> findByPredicate(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return Collections.unmodifiableList(result);
    }
}
