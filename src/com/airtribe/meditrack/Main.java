package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.IdGenerator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService();

        Scanner sc = new Scanner(System.in);
        IdGenerator idGenerator = IdGenerator.getInstance();

        // Load persisted data (doctors and patients) and bootstrap ID generator
        int maxExistingId = 0;
        try {
            var loadedDoctors = CSVUtil.loadDoctorsFromCSV(Constants.DOCTOR_FILE);
            for (Doctor d : loadedDoctors) {
                try {
                    doctorService.addDoctor(d);
                    if (d.getId() > maxExistingId) maxExistingId = d.getId();
                } catch (InvalidDataException e) {
                    System.out.println("Skipped invalid doctor from CSV: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load doctors: " + e.getMessage());
        }

        try {
            var loadedPatients = CSVUtil.loadPatientsFromCSV(Constants.PATIENT_FILE);
            for (Patient p : loadedPatients) {
                try {
                    patientService.addPatient(p);
                    if (p.getId() > maxExistingId) maxExistingId = p.getId();
                } catch (InvalidDataException e) {
                    System.out.println("Skipped invalid patient from CSV: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load patients: " + e.getMessage());
        }

        if (maxExistingId > 0) {
            idGenerator.ensureAtLeast(maxExistingId);
        }

        while (true) {

            System.out.println("\n===== MEDI TRACK MENU =====");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Book Appointment");
            System.out.println("4. View Doctors");
            System.out.println("5. View Patients");
            System.out.println("6. View Appointments");
            System.out.println("8. Generate Bill");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    int dId = idGenerator.generateId();
                    System.out.println("Assigned Doctor ID: " + dId);

                    System.out.print("Enter Name: ");
                    String dName = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int dAge = sc.nextInt();

                    System.out.print("Enter Fee: ");
                    double fee = sc.nextDouble();
                    sc.nextLine();

                    Doctor doctor = new Doctor(
                        dId, dName, dAge,
                        Specialization.CARDIOLOGY,
                        fee
                    );

                    try {
                        doctorService.addDoctor(doctor);
                    } catch (InvalidDataException e) {
                        System.out.println("Error adding doctor: " + e.getMessage());
                    }
                    break;

                case 2:
                    int pId = idGenerator.generateId();
                    System.out.println("Assigned Patient ID: " + pId);

                    System.out.print("Enter Name: ");
                    String pName = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int pAge = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Disease: ");
                    String disease = sc.nextLine();

                    Patient patient = new Patient(pId, pName, pAge, disease);
                    try {
                        patientService.addPatient(patient);
                    } catch (InvalidDataException e) {
                        System.out.println("Error adding patient: " + e.getMessage());
                    }
                    break;

                case 3:
                    int aId = idGenerator.generateId();
                    System.out.println("Assigned Appointment ID: " + aId);

                    System.out.print("Enter Patient ID: ");
                    int patientId = sc.nextInt();

                    System.out.print("Enter Doctor ID: ");
                    int doctorId = sc.nextInt();
                    sc.nextLine();

                    Patient foundPatient =
                        patientService.findPatientById(patientId);

                    Doctor foundDoctor =
                        doctorService.findDoctorById(doctorId);

                    try {
                        if (foundPatient != null && foundDoctor != null) {
                            appointmentService.bookAppointment(
                                    aId,
                                    foundPatient,
                                    foundDoctor,
                                    LocalDate.now()
                            );
                        } else {
                            System.out.println("Doctor or Patient not found!");
                        }
                    } catch (InvalidDataException e) {
                        System.out.println("Invalid appointment data: " + e.getMessage());
                    } catch (AppointmentNotFoundException e) {
                        System.out.println("Appointment error: " + e.getMessage());
                    }
                    break;

                case 4:
                    doctorService.displayAllDoctors();
                    break;

                case 5:
                    patientService.displayAllPatients();
                    break;

                case 6:
                    appointmentService.displayAppointments();
                    break;

                case 8:
                    System.out.print("Enter Appointment ID: ");
                    int billAppointmentId = sc.nextInt();
                    sc.nextLine();
                    try {
                        com.airtribe.meditrack.entity.BillSummary summary = appointmentService.generateBill(billAppointmentId);
                        System.out.println(summary.toString());
                    } catch (AppointmentNotFoundException e) {
                        System.out.println("Could not generate bill: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Saving data and exiting...");
                    try {
                        CSVUtil.saveDoctorsToCSV(doctorService.getAllDoctors(), Constants.DOCTOR_FILE);
                    } catch (IOException e) {
                        System.out.println("Failed to save doctors: " + e.getMessage());
                    }
                    try {
                        CSVUtil.savePatientsToCSV(patientService.getAllPatients(), Constants.PATIENT_FILE);
                    } catch (IOException e) {
                        System.out.println("Failed to save patients: " + e.getMessage());
                    }
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
