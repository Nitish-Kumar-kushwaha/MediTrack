package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService();

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== MEDI TRACK MENU =====");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Book Appointment");
            System.out.println("4. View Doctors");
            System.out.println("5. View Patients");
            System.out.println("6. View Appointments");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Doctor ID: ");
                    int dId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String dName = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int dAge = sc.nextInt();

                    System.out.print("Enter Fee: ");
                    double fee = sc.nextDouble();

                    Doctor doctor = new Doctor(
                            dId, dName, dAge,
                            Specialization.CARDIOLOGY,
                            fee
                    );

                    doctorService.addDoctor(doctor);
                    break;

                case 2:
                    System.out.print("Enter Patient ID: ");
                    int pId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String pName = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int pAge = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Disease: ");
                    String disease = sc.nextLine();

                    Patient patient = new Patient(pId, pName, pAge, disease);
                    patientService.addPatient(patient);
                    break;

                case 3:
                    System.out.print("Enter Appointment ID: ");
                    int aId = sc.nextInt();

                    System.out.print("Enter Patient ID: ");
                    int patientId = sc.nextInt();

                    System.out.print("Enter Doctor ID: ");
                    int doctorId = sc.nextInt();

                    Patient foundPatient =
                            patientService.findPatientById(patientId);

                    Doctor foundDoctor =
                            doctorService.findDoctorById(doctorId);

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

                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
