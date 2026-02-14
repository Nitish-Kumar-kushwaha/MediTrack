package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;
import java.time.LocalDate;
import java.util.List;

public class AppointmentService {

    private final DataStore<Appointment> appointmentStore = new DataStore<>();

    // Book Appointment
    public void bookAppointment(int appointmentId,
                                Patient patient,
                                Doctor doctor,
                                LocalDate date) {

        Appointment appointment = new Appointment(appointmentId, patient, doctor, date);

        appointmentStore.add(appointment);
        System.out.println("Appointment booked successfully!");
    }

    // Cancel Appointment
    public void cancelAppointment(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.cancel();
        System.out.println("Appointment cancelled!");
    }

    /**
     * Finds an appointment by id or throws {@link AppointmentNotFoundException} when not present.
     *
     * @param appointmentId the id to search for
     * @return the found {@link Appointment}
     * @throws AppointmentNotFoundException if no appointment exists with the given id
     */
    public Appointment findAppointmentById(int appointmentId) {
        return appointmentStore.findById(Appointment::getAppointmentId, appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + appointmentId + " not found"));
    }

    /**
     * Generates a bill summary for the appointment with the given id.
     *
     * @param appointmentId id of the appointment to bill
     * @return {@link com.airtribe.meditrack.entity.BillSummary} for the appointment
     * @throws AppointmentNotFoundException if the appointment cannot be found
     */
    public com.airtribe.meditrack.entity.BillSummary generateBill(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        Bill bill = new Bill(appointment);
        return bill.generateBillSummary();
    }

    // Display All Appointments
    public void displayAppointments() {
        List<Appointment> all = appointmentStore.getAll();
        for (Appointment appointment : all) {
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
