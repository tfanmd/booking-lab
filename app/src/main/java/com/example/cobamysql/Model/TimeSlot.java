package com.example.cobamysql.Model;

public class TimeSlot {
    public enum Status { AVAILABLE, UNAVAILABLE, SELECTED }

    private String time;
    private Status status;

    public TimeSlot(String time, Status status) {
        this.time = time;
        this.status = status;
    }
    public String getTime() { return time; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
