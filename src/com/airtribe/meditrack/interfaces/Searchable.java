package com.airtribe.meditrack.interfaces;

/**
 * Represents an object that can be searched by a keyword.
 *
 * Java 8 compatible: this interface declares a single abstract method
 * and provides a {@code default} helper for exact-match semantics.
 */
@FunctionalInterface
public interface Searchable {

    /**
     * Returns {@code true} when this object matches the provided keyword.
     * Implementations may define flexible matching rules (contains, startsWith, etc.).
     *
     * @param keyword search keyword (may be {@code null})
     * @return {@code true} if the object matches the keyword
     */
    boolean matches(String keyword);

    /**
     * Default exact-match helper.
     *
     * The default behavior compares the trimmed, case-insensitive {@code keyword}
     * with this object's {@code toString()} value. Implementations can override
     * this method to provide a more specific exact-match definition.
     *
     * @param keyword search keyword
     * @return {@code true} when the keyword exactly matches this object
     */
    default boolean isExactMatch(String keyword) {
        if (keyword == null) {
            return false;
        }
        String trimmed = keyword.trim();
        String repr = String.valueOf(this.toString()).trim();
        return trimmed.equalsIgnoreCase(repr);
    }
}
