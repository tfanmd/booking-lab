package com.example.cobamysql.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cobamysql.Model.DateModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    @NonNull
    @Override
    public DateAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    // ... (constructor, dll)
    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        DateModel dateModel = dateList.get(position);
        LocalDate date = dateModel.getDate();

        // Format tanggal, misal: "20 Jun" dan "Sab"
        holder.tvDate.setText(date.format(DateTimeFormatter.ofPattern("dd MMM")));
        holder.tvDay.setText(date.format(DateTimeFormatter.ofPattern("E")));

        // Logika untuk menandai hari ini
        if (date.equals(LocalDate.now())) {
            // Terapkan background biru
        } else {
            // Terapkan background putih border biru
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    // ... (ViewHolder, dll)
}
