package com.example.cobamysql.Model;
import java.util.Date;

import java.time.LocalDate;

public class DateModel {
    private LocalDate date;
    // ...tambahkan atribut lain jika perlu

    public DateModel(LocalDate date) { this.date = date; }
    public LocalDate getDate() { return date; }
}
