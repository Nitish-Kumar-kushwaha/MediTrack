package com.airtribe.meditrack.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable summary of billing information for an appointment.
 * <p>
 * This class is final, all fields are private and final, and it exposes only
 * getters. {@link LocalDate} is immutable so it is safe to return directly.
 */
public final class BillSummary {

    private final String patientName;
    private final String doctorName;
    private final double totalAmount;
    private final LocalDate appointmentDate;

    /**
     * Constructs a new {@code BillSummary} instance.
     *
     * @param patientName     patient full name, non-null and non-empty
     * @param doctorName      doctor full name, non-null and non-empty
     * @param totalAmount     total amount (including taxes), must be finite and non-negative
     * @param appointmentDate appointment date, non-null
     * @throws NullPointerException     if {@code patientName}, {@code doctorName} or {@code appointmentDate} is null
     * @throws IllegalArgumentException if any string is empty or {@code totalAmount} is invalid
     */
    public BillSummary(String patientName, String doctorName, double totalAmount, LocalDate appointmentDate) {
        this.patientName = Objects.requireNonNull(patientName, "patientName must not be null").trim();
        this.doctorName = Objects.requireNonNull(doctorName, "doctorName must not be null").trim();
        this.appointmentDate = Objects.requireNonNull(appointmentDate, "appointmentDate must not be null");

        if (this.patientName.isEmpty()) {
            throw new IllegalArgumentException("patientName must not be empty");
        }
        if (this.doctorName.isEmpty()) {
            throw new IllegalArgumentException("doctorName must not be empty");
        }
        if (Double.isNaN(totalAmount) || Double.isInfinite(totalAmount) || totalAmount < 0d) {
            throw new IllegalArgumentException("totalAmount must be a finite non-negative number");
        }

        this.totalAmount = totalAmount;
    }

    /**
     * @return the patient's name
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @return the doctor's name
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @return the total billed amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @return the appointment date
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    @Override
    public String toString() {
        return "BillSummary{" +
                "patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", totalAmount=" + totalAmount +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}
