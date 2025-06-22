package com.example.cobamysql.Adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// ... import
public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder> {
    // ... (constructor dengan listener dari TimeSlotAdapter)
    @Override
    public void onBindViewHolder(@NonNull LabViewHolder holder, int position) {
        Lab currentLab = labList.get(position);
        holder.tvLabName.setText(currentLab.getLabName());
        // ...

        // Setup nested RecyclerView untuk jam
        TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(context, currentLab.getTimeSlots(), listener, position);
        holder.rvTimeSlots.setLayoutManager(new GridLayoutManager(context, 4));
        holder.rvTimeSlots.setAdapter(timeSlotAdapter);
    }
    // ... (ViewHolder, dll)
}
