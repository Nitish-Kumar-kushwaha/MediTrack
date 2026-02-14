package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private List<Patient> patients = new ArrayList<>();

    // Add Patient
    public void addPatient(Patient patient) {
        patients.add(patient);
        System.out.println("Patient added successfully!");
    }

    // Find Patient by ID
    public Patient findPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    // Display All Patients
    public void displayAllPatients() {
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getId()
                    + ", Name: " + patient.getName()
                    + ", Age: " + patient.getAge()
                    + ", Disease: " + patient.getDisease());
        }
    }
}
