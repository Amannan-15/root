package com.project.backend;

import java.util.ArrayList;
import java.util.List;

public class ShiftManager {
    private List<Shift> shifts = new ArrayList<>();
    private int nextId = 1;

    // Add a new shift
    public Shift addShift(String employeeName, String date, String startTime, String endTime) {
        Shift shift = new Shift(nextId++, employeeName, date, startTime, endTime);
        shifts.add(shift);
        return shift;
    }

    // Get all shifts
    public List<Shift> getShifts() {
        return shifts;
    }

    // Find a shift by ID
    public Shift findShiftById(int id) {
        return shifts.stream().filter(shift -> shift.getShiftId() == id).findFirst().orElse(null);
    }

    // Update an existing shift
    public boolean updateShift(int id, String employeeName, String date, String startTime, String endTime) {
        Shift shift = findShiftById(id);
        if (shift != null) {
            shift.setEmployeeName(employeeName);
            shift.setDate(date);
            shift.setStartTime(startTime);
            shift.setEndTime(endTime);
            return true;
        }
        return false;
    }

    // Delete a shift by ID
    public boolean deleteShift(int id) {
        boolean removed = shifts.removeIf(shift -> shift.getShiftId() == id);
        if (removed) {
            // Reassign IDs to maintain sequential order
            for (int i = 0; i < shifts.size(); i++) {
                shifts.get(i).setShiftId(i + 1);
            }
            nextId = shifts.size() + 1; // Update nextId to the next available ID
        }
        return removed;
    }
    
   

    // Display all shifts
    public void displayAllShifts() {
        if (shifts.isEmpty()) {
            System.out.println("No shifts available.");
        } else {
            shifts.forEach(System.out::println);
        }
    }

    // Save shifts to a CSV file
    public void saveToCsv(String filePath) {
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"ShiftID", "EmployeeName", "Date", "StartTime", "EndTime"});
        for (Shift shift : shifts) {
            csvData.add(new String[]{
                String.valueOf(shift.getShiftId()),
                shift.getEmployeeName(),
                shift.getDate(),
                shift.getStartTime(),
                shift.getEndTime()
            });
        }
        CSVHandler.writeToCSV(filePath, csvData);
    }

    // Load shifts from a CSV file
    public void loadFromCsv(String filePath) {
        List<String[]> csvData = CSVHandler.readFromCSV(filePath);
        shifts.clear();
        for (int i = 1; i < csvData.size(); i++) {
            String[] row = csvData.get(i);
            shifts.add(new Shift(
                Integer.parseInt(row[0 ]),
                row[1],
                row[2],
                row[3],
                row[4]
            ));
        }
        nextId = shifts.isEmpty() ? 1 : shifts.get(shifts.size() - 1).getShiftId() + 1;
    }
}