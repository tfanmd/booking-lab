package com.example.cobamysql.Model;
import java.util.List;

import java.util.List;

public class Lab {
    private String labName;
    private String labDescription;
    private List<TimeSlot> timeSlots;

    public Lab(String labName, String labDescription, List<TimeSlot> timeSlots) {
        this.labName = labName;
        this.labDescription = labDescription;
        this.timeSlots = timeSlots;
    }
    // ...Getters...
}
