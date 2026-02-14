package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Patient;
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

/**
 * Utility for simple CSV import/export of {@link Patient} records.
 *
 * Notes:
 * - Uses simple comma-separated values and {@code String.split(",")} for parsing.
 * - Uses try-with-resources to manage IO streams.
 */
public final class CSVUtil {

    private CSVUtil() {
        throw new AssertionError("CSVUtil is a utility class and cannot be instantiated");
    }

    /**
     * Saves the given patients to the specified CSV file. Each line uses the
     * format: id,name,age,disease
     *
     * @param patients list of patients to save; if null nothing will be written
     * @param filePath path to output CSV file
     * @throws IOException when an IO error occurs while writing the file
     */
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

    /**
     * Loads patients from the specified CSV file. Expects lines in the format:
     * id,name,age,disease
     *
     * @param filePath path to input CSV file
     * @return list of patients (empty list if file does not exist or contains no valid lines)
     * @throws IOException when an IO error occurs while reading the file or when a line cannot be parsed
     */
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
}
