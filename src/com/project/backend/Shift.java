package com.project.backend;

 class Shift{
    private int shiftID;
    private String employeeName;
    private String Date;
    private String startTime;
    private String endTime;

    public Shift(int shiftID, String employeeName, String Date, String startTime, String endTime){
        this.shiftID = shiftID;
        this.employeeName = employeeName;
        this.Date = Date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    //getters
    public String getDate() {
        return Date;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public String getEndTime() {
        return endTime;
    }
    public int getShiftID() {
        return shiftID;
    }
    public String getStartTime() {
        return startTime;
    }

    //setters
    public void setDate(String date) {
        Date = date;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String toString(){
        return shiftID + " | " + employeeName + " | " + Date + " | " + startTime + " | " + endTime;
    }
    
    public static void main(String[] args){
        Shift newShift = new Shift(1, "Andres", "25/09/2024" , "12:30 PM", "9:30 AM");
        System.out.println(newShift.toString());
    }
}