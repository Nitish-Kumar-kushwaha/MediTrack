package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {

    private final DataStore<Doctor> doctorStore = new DataStore<>();

    // Add Doctor
    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new InvalidDataException("Doctor must not be null");
        }

        Validator.validateName(doctor.getName());
        Validator.validateAge(doctor.getAge());
        Validator.validateConsultationFee(doctor.getConsultationFee());

        doctorStore.add(doctor);
        System.out.println("Doctor added successfully!");
    }

    // Find Doctor by ID
    public Doctor findDoctorById(int id) {
        return doctorStore.findById(Doctor::getId, id).orElse(null);
    }

    // Display All Doctors
    public void displayAllDoctors() {
        doctorStore.getAll().forEach(doctor -> System.out.println("ID: " + doctor.getId()
                + ", Name: " + doctor.getName()
                + ", Specialization: " + doctor.getSpecialization()
                + ", Fee: " + doctor.getConsultationFee()));
    }

    /**
     * Finds doctors by their specialization.
     *
     * @param specialization the specialization to filter by
     * @return list of doctors with the given specialization
     */
    public List<Doctor> findDoctorsBySpecialization(Specialization specialization) {
        if (specialization == null) {
            return List.of();
        }
        return doctorStore.getAll().stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    /**
     * Returns the average consultation fee across all doctors.
     *
     * @return average fee, or 0.0 if no doctors exist
     */
    public double getAverageConsultationFee() {
        return doctorStore.getAll().stream()
            .mapToDouble(Doctor::getConsultationFee)
            .average()
            .orElse(0.0);
    }

    /**
     * Counts the number of doctors in the service.
     *
     * @return count of doctors
     */
    public long countDoctors() {
        return doctorStore.getAll().stream().count();
    }

    /**
     * Returns all doctors currently stored.
     *
     * @return unmodifiable list of doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
}
