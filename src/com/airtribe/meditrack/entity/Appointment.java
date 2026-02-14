package com.airtribe.meditrack.entity;

import java.time.LocalDate;

/**
 * Represents an appointment between a patient and a doctor on a specific date.
 * <p>
 * This class implements {@link Cloneable} and provides a {@link #clone()} method
 * that produces a deep copy of the appointment, including cloning the
 * {@link Patient} and creating a defensive copy of the {@link Doctor} data.
 */
public class Appointment implements Cloneable {

    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate appointmentDate;
    private AppointmentStatus status;

    public Appointment(int appointmentId,
                       Patient patient,
                       Doctor doctor,
                       LocalDate appointmentDate) {

        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.status = AppointmentStatus.PENDING; // default
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    /**
     * Creates and returns a deep copy of this {@code Appointment}.
     * <p>
     * The returned object is a new {@code Appointment} with the same primitive
     * values and independent copies of the nested objects. The {@link Patient}
     * is cloned via its {@code clone()} implementation; the {@link Doctor}
     * is copied by constructing a new {@code Doctor} instance using its
     * exposed getters. The appointment {@link AppointmentStatus} and
     * {@link LocalDate} are immutable or treated as such and are preserved.
     *
     * @return a deep copy of this appointment
     */
    @Override
    public Appointment clone() {
        try {
            // Attempt a shallow clone and then deep copy referenced fields
            Appointment cloned = (Appointment) super.clone();

            // Deep-copy patient
            cloned.patient = this.patient == null ? null : this.patient.clone();

            // Defensive copy for doctor by constructing a new instance
            if (this.doctor == null) {
                cloned.doctor = null;
            } else {
                cloned.doctor = new Doctor(
                        this.doctor.getId(),
                        this.doctor.getName(),
                        this.doctor.getAge(),
                        this.doctor.getSpecialization(),
                        this.doctor.getConsultationFee()
                );
            }

            // LocalDate and enums are immutable — assign directly
            cloned.appointmentDate = this.appointmentDate;
            cloned.status = this.status;

            return cloned;
        } catch (CloneNotSupportedException e) {
            // Fallback: manual construction to ensure deep copy semantics
            Patient patientCopy = this.patient == null ? null : this.patient.clone();
            Doctor doctorCopy = null;
            if (this.doctor != null) {
                doctorCopy = new Doctor(
                        this.doctor.getId(),
                        this.doctor.getName(),
                        this.doctor.getAge(),
                        this.doctor.getSpecialization(),
                        this.doctor.getConsultationFee()
                );
            }

            Appointment copy = new Appointment(this.appointmentId, patientCopy, doctorCopy, this.appointmentDate);
            if (this.status == AppointmentStatus.CONFIRMED) copy.confirm();
            if (this.status == AppointmentStatus.CANCELLED) copy.cancel();
            return copy;
        }
    }
}
