package com.airtribe.meditrack.entity;

import java.time.LocalDate;

public class Appointment {

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
}
