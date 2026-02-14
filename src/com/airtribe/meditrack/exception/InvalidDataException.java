package com.airtribe.meditrack.exception;

/**
 * Exception thrown when invalid domain data is encountered during validation.
 */
public class InvalidDataException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidDataException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code InvalidDataException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the cause of this exception (which is saved for later retrieval by
     *                {@link #getCause()}).  A {@code null} value is permitted and indicates
     *                that the cause is nonexistent or unknown.
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
