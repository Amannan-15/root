package com.project.backend;

public class Shift {
    private int shiftId;           
    private String employeeName;   
    private String date;           
    private String startTime;      
    private String endTime;        

    public Shift(int shiftId, String employeeName, String date, String startTime, String endTime) {
        this.shiftId = shiftId;
        this.employeeName = employeeName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

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
    
    
    @Override
    public String toString() {
        return String.format("%d | %s | %s | %s | %s", shiftId, employeeName, date, startTime, endTime);
    }
}