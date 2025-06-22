package com.example.cobamysql.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cobamysql.Model.TimeSlot;

// ... import
public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(int labPosition, int timePosition);
    }
    // ... (constructor dengan listener)

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        TimeSlot timeSlot = timeSlots.get(position);
        holder.tvTime.setText(timeSlot.getTime());

        // Atur tampilan berdasarkan status
        switch (timeSlot.getStatus()) {
            case AVAILABLE:
                holder.itemView.setBackgroundResource(R.drawable.timeslot_available_bg);
                holder.tvTime.setTextColor(context.getResources().getColor(R.color.biru_utama));
                break;
            case UNAVAILABLE:
                holder.itemView.setBackgroundResource(R.drawable.timeslot_unavailable_bg);
                holder.tvTime.setTextColor(Color.DKGRAY);
                break;
            case SELECTED:
                holder.itemView.setBackgroundResource(R.drawable.bg_timeslot_selected);
                holder.tvTime.setTextColor(Color.WHITE);
                break;
        }

        // Tangani klik
        holder.itemView.setOnClickListener(v -> {
            if (timeSlot.getStatus() != TimeSlot.Status.UNAVAILABLE) {
                listener.onTimeSlotClick(labPosition, position);
            }
        });
    }
    // ... (ViewHolder, dll)
}
