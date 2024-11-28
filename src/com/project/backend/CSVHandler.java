package com.project.backend;

import java.io.*;
import java.util.List;

public class CSVHandler {

    public static void saveShiftsToCsv(String filePath, List<Shift> shifts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Shift shift : shifts) {
                writer.write(shift.getShiftID() + "," +
                             shift.getEmployeeName() + "," +
                             shift.getDate() + "," +
                             shift.getStartTime() + "," +
                             shift.getEndTime());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving shifts to CSV: " + e.getMessage());
        }
    }

    public static void loadShiftsFromCsv(String filePath, ShiftManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String employeeName = parts[1];
                String date = parts[2];
                String startTime = parts[3];
                String endTime = parts[4];

                manager.getShifts().add(new Shift(id, employeeName, date, startTime, endTime));
            }
            manager.setNextId();
        } catch (IOException e) {
            System.out.println("Error loading shifts from CSV: " + e.getMessage());
        }
    }
}
