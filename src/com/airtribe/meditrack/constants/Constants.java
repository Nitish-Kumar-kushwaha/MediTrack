package com.airtribe.meditrack.constants;

/**
 * Application-wide constants.
 */
public final class Constants {

    /** Tax rate expressed as a decimal (e.g. 0.10 for 10%). */
    public static final double TAX_RATE = 0.10d;

    /** Default CSV file name for doctors. */
    public static final String DOCTOR_FILE = "doctors.csv";

    /** Default CSV file name for patients. */
    public static final String PATIENT_FILE = "patients.csv";

    private Constants() {
        throw new AssertionError("Constants class");
    }
}
