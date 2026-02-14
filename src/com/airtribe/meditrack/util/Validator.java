package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Utility class providing static validation methods for common domain fields.
 */
public final class Validator {

    private Validator() {
        throw new AssertionError("Validator is a utility class and cannot be instantiated");
    }
    /**
     * Validates that a name is provided.
     *
     * @param name the name to validate
     * @throws InvalidDataException if {@code name} is {@code null} or blank
     */
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name must not be null or empty");
        }
    }

    /**
     * Validates that an age is a positive integer.
     *
     * @param age the age to validate
     * @throws InvalidDataException if {@code age} is less than or equal to zero
     */
    public static void validateAge(int age) {
        if (age <= 0) {
            throw new InvalidDataException("Age must be greater than 0");
        }
    }

    /**
     * Validates that the consultation fee is a positive number.
     *
     * @param fee the consultation fee to validate
     * @throws InvalidDataException if {@code fee} is NaN or not positive
     */
    public static void validateConsultationFee(double fee) {
        if (Double.isNaN(fee) || fee <= 0d) {
            throw new InvalidDataException("Consultation fee must be positive");
        }
    }

    /**
     * Validates that a disease description is provided.
     *
     * @param disease the disease description to validate
     * @throws InvalidDataException if {@code disease} is {@code null} or blank
     */
    public static void validateDisease(String disease) {
        if (disease == null || disease.trim().isEmpty()) {
            throw new InvalidDataException("Disease must not be null or empty");
        }
    }
}
