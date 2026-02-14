package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a bill for a single appointment. The class copies required
 * primitive/immutable data from {@link Appointment} at construction time so
 * internal mutable objects are not exposed.
 */
public final class Bill {

    private final String patientName;
    private final String doctorName;
    private final double consultationFee;
    private final LocalDate appointmentDate;

    /**
     * Construct a new bill by copying required data from the appointment.
     *
     * @param appointment the source appointment, must not be null
     */
    public Bill(Appointment appointment) {
        Objects.requireNonNull(appointment, "appointment must not be null");
        this.patientName = Objects.requireNonNull(appointment.getPatient().getName(), "patient name must not be null");
        this.doctorName = Objects.requireNonNull(appointment.getDoctor().getName(), "doctor name must not be null");
        this.consultationFee = appointment.getDoctor().getConsultationFee();
        this.appointmentDate = Objects.requireNonNull(appointment.getAppointmentDate(), "appointment date must not be null");
    }

    /**
     * Generates an immutable {@link BillSummary} containing patient/doctor names,
     * the appointment date and the total amount including tax (using {@link Constants#TAX_RATE}).
     *
     * @return a {@link BillSummary} with computed total
     */
    public BillSummary generateBillSummary() {
        BigDecimal feeBd = BigDecimal.valueOf(consultationFee);
        BigDecimal taxRate = BigDecimal.valueOf(Constants.TAX_RATE);

        BigDecimal tax = feeBd.multiply(taxRate);
        BigDecimal total = feeBd.add(tax).setScale(2, RoundingMode.HALF_UP);

        return new BillSummary(patientName, doctorName, total.doubleValue(), appointmentDate);
    }
}
