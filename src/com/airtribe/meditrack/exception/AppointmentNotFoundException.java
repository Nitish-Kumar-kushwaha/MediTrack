package com.airtribe.meditrack.exception;

/**
 * Exception thrown when an appointment with the specified ID cannot be found.
 * <p>
 * This is a runtime exception used by services that lookup appointments by ID
 * and should be thrown when no appointment exists for the provided identifier.
 *
 * @since 1.0
 */
public class AppointmentNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code AppointmentNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining why the appointment was not found
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
