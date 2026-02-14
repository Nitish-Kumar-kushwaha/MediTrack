package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public final class CSVUtil {

    private CSVUtil() {
        throw new AssertionError("CSVUtil is a utility class and cannot be instantiated");
    }

    
    public static void savePatientsToCSV(List<Patient> patients, String filePath) throws IOException {
        if (patients == null) {
            return;
        }

        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Patient p : patients) {
                String line = String.format("%d,%s,%d,%s",
                        p.getId(), p.getName(), p.getAge(), p.getDisease());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    
    public static List<Patient> loadPatientsFromCSV(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<Patient> result = new ArrayList<>();

        if (!Files.exists(path)) {
            return result;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 4) {
                    throw new IOException("Invalid CSV line, expected 4 columns: " + line);
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String disease = parts[3].trim();
                    Patient patient = new Patient(id, name, age, disease);
                    result.add(patient);
                } catch (NumberFormatException ex) {
                    throw new IOException("Failed to parse numeric value from line: " + line, ex);
                }
            }
        }

        return result;
    }

    
    public static void saveDoctorsToCSV(List<Doctor> doctors, String filePath) throws IOException {
        if (doctors == null) {
            return;
        }

        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Doctor d : doctors) {
                String line = String.format("%d,%s,%d,%s,%.2f",
                        d.getId(), d.getName(), d.getAge(),
                        d.getSpecialization() == null ? "" : d.getSpecialization().name(),
                        d.getConsultationFee());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    
    public static List<Doctor> loadDoctorsFromCSV(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<Doctor> result = new ArrayList<>();

        if (!Files.exists(path)) {
            return result;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    throw new IOException("Invalid CSV line, expected 5 columns: " + line);
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String spec = parts[3].trim();
                    Specialization specialization = spec.isEmpty() ? null : Specialization.valueOf(spec);
                    double fee = Double.parseDouble(parts[4].trim());

                    Doctor doctor = new Doctor(id, name, age, specialization, fee);
                    result.add(doctor);
                } catch (IllegalArgumentException ex) {
                    throw new IOException("Failed to parse line: " + line, ex);
                }
            }
        }

        return result;
    }
}
