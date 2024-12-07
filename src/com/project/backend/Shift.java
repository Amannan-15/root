package com.project.backend;

/**
 * Represents a work shift assigned to an employee.
 */
public class Shift {
    private int shiftId;           // Unique ID for the shift
    private String employeeName;   // Name of the employee assigned to the shift
    private String date;           // Date of the shift (e.g., "2024-11-30")
    private String startTime;      // Start time of the shift (e.g., "9:00 AM")
    private String endTime;        // End time of the shift (e.g., "5:00 PM")

    // Constructor
    public Shift(int shiftId, String employeeName, String date, String startTime, String endTime) {
        this.shiftId = shiftId;
        this.employeeName = employeeName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public int getShiftId() {
        return shiftId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    // Setters
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setShiftId(int shiftId) {
        //System.out.println("setShiftId called with: " + shiftId);
        this.shiftId = shiftId;
    }
    
    
    // toString method for displaying shift details
    @Override
    public String toString() {
        return String.format("%d | %s | %s | %s | %s", shiftId, employeeName, date, startTime, endTime);
    }
}