package com.project.backend;

import java.util.ArrayList;
import java.util.List;

public class ShiftManager {
    private List<Shift> shifts = new ArrayList<>();
    private int nextId = 1; 

    public Shift addShift(String employeeName, String date, String startTime, String endTime) {
        Shift shift = new Shift(nextId++, employeeName, date, startTime, endTime);
        shifts.add(shift);
        return shift; 
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public Shift findShiftById(int id) {
        for (Shift shift : shifts) {
            if (shift.getShiftID() == id) {
                return shift;
            }
        }
        return null; 
    }

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

    public boolean deleteShift(int id) {
        return shifts.removeIf(shift -> shift.getShiftID() == id); 
    }

    public void setNextId() {
    int maxId = 0;
    for (Shift shift : shifts) {
        if (shift.getShiftID() > maxId) {
            maxId = shift.getShiftID();
        }
    }
    nextId = maxId + 1;
}

    public void displayAllShifts() {
        if (shifts.isEmpty()) {
            System.out.println("No shifts available.");
        } else {
            for (Shift shift : shifts) {
                System.out.println(shift);
            }
        }
    }
}
