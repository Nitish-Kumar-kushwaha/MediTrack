package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Utility class providing static validation methods for common domain fields.
 */
public final class Validator {

    private Validator() {
        throw new AssertionError("Validator is a utility class and cannot be instantiated");
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name must not be null or empty");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0) {
            throw new InvalidDataException("Age must be greater than 0");
        }
    }

    public static void validateConsultationFee(double fee) {
        if (Double.isNaN(fee) || fee <= 0d) {
            throw new InvalidDataException("Consultation fee must be positive");
        }
    }

    public static void validateDisease(String disease) {
        if (disease == null || disease.trim().isEmpty()) {
            throw new InvalidDataException("Disease must not be null or empty");
        }
    }
}
