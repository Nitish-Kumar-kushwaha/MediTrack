package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A simple, generic in-memory data store for domain objects.
 * <p>
 * The class is intentionally lightweight and exposes basic operations to add,
 * remove and retrieve items. Consumers provide an {@link Function} that
 * extracts an integer identifier from stored objects for lookups.
 *
 * This implementation keeps a private list of items and returns defensive
 * copies where appropriate to preserve encapsulation.
 *
 * @param <T> the type of objects stored
 */
public final class DataStore<T> {

    private final List<T> items = new ArrayList<>();

    /**
     * Prevent instantiation; this class is intended to be used as a component.
     */
    public DataStore() {
    }

    /**
     * Adds an item to the store.
     *
     * @param item the item to add; must not be {@code null}
     * @throws InvalidDataException if {@code item} is {@code null}
     */
    public synchronized void add(T item) {
        if (item == null) {
            throw new InvalidDataException("Item to add must not be null");
        }
        items.add(item);
    }

    /**
     * Removes an item from the store.
     *
     * @param item the item to remove
     * @return {@code true} if the item was present and removed, {@code false} otherwise
     */
    public synchronized boolean remove(T item) {
        return items.remove(item);
    }

    /**
     * Returns an unmodifiable list of all stored items.
     *
     * @return an unmodifiable copy of the items
     */
    public synchronized List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    /**
     * Finds an item by its integer identifier.
     *
     * @param idExtractor function that extracts the integer id from an item; must not be {@code null}
     * @param id          the id to search for
     * @return an {@link Optional} containing the found item, or an empty {@link Optional} if not found
     * @throws InvalidDataException if {@code idExtractor} is {@code null}
     */
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
