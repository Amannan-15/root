package com.project.backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    // Write data to a CSV file
    public static void writeToCSV(String filePath, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    // Read data from a CSV file
    public static List<String[]> readFromCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV: " + e.getMessage());
        }
        return data;
    }
}