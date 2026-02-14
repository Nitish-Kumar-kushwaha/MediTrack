package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;
import java.util.List;

public class PatientService {

    private final DataStore<Patient> patientStore = new DataStore<>();

    // Add Patient
    public void addPatient(Patient patient) {
        if (patient == null) {
            throw new InvalidDataException("Patient must not be null");
        }

        Validator.validateName(patient.getName());
        Validator.validateAge(patient.getAge());
        Validator.validateDisease(patient.getDisease());

        patientStore.add(patient);
        System.out.println("Patient added successfully!");
    }

    // Find Patient by ID
    public Patient findPatientById(int id) {
        return patientStore.findById(Patient::getId, id).orElse(null);
    }

    // Display All Patients
    public void displayAllPatients() {
        List<Patient> all = patientStore.getAll();
        for (Patient patient : all) {
            System.out.println("ID: " + patient.getId()
                    + ", Name: " + patient.getName()
                    + ", Age: " + patient.getAge()
                    + ", Disease: " + patient.getDisease());
        }
    }

    /**
     * Returns all patients currently stored.
     *
     * @return unmodifiable list of patients
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
}
