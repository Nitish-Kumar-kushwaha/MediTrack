package com.airtribe.meditrack.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable summary of a bill for an appointment.
 *
 * All fields are private and final. The class is final and exposes only getters.
 */
public final class BillSummary {

    private final String patientName;
    private final String doctorName;
    private final BigDecimal totalAmount;
    private final LocalDate appointmentDate;

    /**
     * Constructs a new {@code BillSummary} with the provided values.
     *
     * @param patientName     the patient's name, must not be null
     * @param doctorName      the doctor's name, must not be null
     * @param totalAmount     the total amount charged, must not be null
     * @param appointmentDate the appointment date, must not be null
     * @throws NullPointerException if any argument is null
     */
    public BillSummary(String patientName, String doctorName, BigDecimal totalAmount, LocalDate appointmentDate) {
        this.patientName = Objects.requireNonNull(patientName, "patientName must not be null");
        this.doctorName = Objects.requireNonNull(doctorName, "doctorName must not be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "totalAmount must not be null");
        this.appointmentDate = Objects.requireNonNull(appointmentDate, "appointmentDate must not be null");
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
     * @return the total amount charged
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @return the appointment date
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillSummary that = (BillSummary) o;
        return patientName.equals(that.patientName) && doctorName.equals(that.doctorName) && totalAmount.equals(that.totalAmount) && appointmentDate.equals(that.appointmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, doctorName, totalAmount, appointmentDate);
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
