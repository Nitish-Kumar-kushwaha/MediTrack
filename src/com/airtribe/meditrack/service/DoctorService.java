package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {

    private final List<Doctor> doctors = new ArrayList<>();

    // Add Doctor
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        System.out.println("Doctor added successfully!");
    }

    // Find Doctor by ID
    public Doctor findDoctorById(int id) {
        return doctors.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Display All Doctors
    public void displayAllDoctors() {
        doctors.forEach(doctor -> System.out.println("ID: " + doctor.getId()
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
            return new ArrayList<>();
        }
        return doctors.stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    /**
     * Returns the average consultation fee across all doctors.
     *
     * @return average fee, or 0.0 if no doctors exist
     */
    public double getAverageConsultationFee() {
        return doctors.stream()
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
        return doctors.stream().count();
    }
}
