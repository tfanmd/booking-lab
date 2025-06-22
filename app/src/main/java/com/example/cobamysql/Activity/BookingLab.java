package com.example.cobamysql.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cobamysql.Activity.FormBooking;
import com.example.cobamysql.Adapter.DateAdapter;
import com.example.cobamysql.Adapter.LabAdapter;
import com.example.cobamysql.Adapter.TimeSlotAdapter;
import com.example.cobamysql.Model.Lab;
import com.example.cobamysql.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TimeSlotAdapter.OnTimeSlotClickListener {

    private List<Lab> labList = new ArrayList<>();
    private LabAdapter labAdapter;
    private int lastSelectedLab = -1;
    private int lastSelectedTime = -1;

    // Variabel untuk data yang akan dikirim
    private String selectedLabInfo = null;
    private String selectedTimeInfo = null;
    private String selectedDateInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- SETUP TANGGAL ---
        RecyclerView rvDates = findViewById(R.id.rv_dates);
        rvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDates.setAdapter(new DateAdapter(this, generateDateList()));
        // TODO: Tangani klik tanggal dan simpan di selectedDateInfo

        // --- SETUP LAB & JAM ---
        RecyclerView rvLabs = findViewById(R.id.rv_labs);
        rvLabs.setLayoutManager(new LinearLayoutManager(this));
        generateLabList(); // Buat data dummy
        labAdapter = new LabAdapter(this, labList, this);
        rvLabs.setAdapter(labAdapter);

        // --- SETUP TOMBOL BOOKING ---
        TextView btnBooking = findViewById(R.id.booking);
        btnBooking.setOnClickListener(v -> {
            if (selectedTimeInfo == null) {
                Toast.makeText(this, "Jam belum dipilih", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("EXTRA_LAB", selectedLabInfo);
                intent.putExtra("EXTRA_TIME", selectedTimeInfo);
                intent.putExtra("EXTRA_DATE", "Jumat, 20 Juni 2025"); // Ganti dengan tanggal yg dipilih
                startActivity(intent);
            }
        });
    }

    // --- Implementasi dari TimeSlotAdapter.OnTimeSlotClickListener ---
    @Override
    public void onTimeSlotClick(int labPosition, int timePosition) {
        // 1. Reset pilihan lama (jika ada)
        if (lastSelectedLab != -1 && lastSelectedTime != -1) {
            labList.get(lastSelectedLab).getTimeSlots().get(lastSelectedTime).setStatus(TimeSlot.Status.AVAILABLE);
        }

        // 2. Set pilihan baru
        labList.get(labPosition).getTimeSlots().get(timePosition).setStatus(TimeSlot.Status.SELECTED);

        // 3. Simpan posisi pilihan baru
        lastSelectedLab = labPosition;
        lastSelectedTime = timePosition;

        // 4. Simpan info untuk dikirim via Intent
        selectedLabInfo = labList.get(labPosition).getLabName();
        selectedTimeInfo = labList.get(labPosition).getTimeSlots().get(timePosition).getTime();

        // 5. Beri tahu adapter untuk menggambar ulang tampilannya
        labAdapter.notifyDataSetChanged();
    }

    // --- Helper untuk membuat data dummy ---
    private List<DateModel> generateDateList() {
        List<DateModel> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 14; i++) {
            dates.add(new DateModel(today.plusDays(i)));
        }
        return dates;
    }

    private void generateLabList() {
        String[] times = {"08.00", "10.40", "13.25", "16.10"};
        Random random = new Random();

        for (int i = 2; i <= 11; i++) {
            List<TimeSlot> timeSlots = new ArrayList<>();
            for (String time : times) {
                // Buat beberapa jam unavailable secara acak
                if (random.nextBoolean()) {
                    timeSlots.add(new TimeSlot(time, TimeSlot.Status.AVAILABLE));
                } else {
                    timeSlots.add(new TimeSlot(time, TimeSlot.Status.UNAVAILABLE));
                }
            }
            labList.add(new Lab("Lab " + i, "Deskripsi untuk Lab " + i, timeSlots));
        }
    }
}