package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    private List<Appointment> appointments = new ArrayList<>();

    // Book Appointment
    public void bookAppointment(int appointmentId,
                                Patient patient,
                                Doctor doctor,
                                LocalDate date) {

        Appointment appointment =
                new Appointment(appointmentId, patient, doctor, date);

        appointments.add(appointment);
        System.out.println("Appointment booked successfully!");
    }

    // Cancel Appointment
    public void cancelAppointment(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                appointment.cancel();
                System.out.println("Appointment cancelled!");
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    // Display All Appointments
    public void displayAppointments() {
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: "
                    + appointment.getAppointmentId()
                    + ", Patient: "
                    + appointment.getPatient().getName()
                    + ", Doctor: "
                    + appointment.getDoctor().getName()
                    + ", Date: "
                    + appointment.getAppointmentDate()
                    + ", Status: "
                    + appointment.getStatus());
        }
    }
}
