package com.airtribe.meditrack.exception;

/**
 * Exception thrown when invalid domain data is encountered during validation.
 */
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
