package com.example.cobamysql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cobamysql.R;
import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

    Context context;
    ArrayList<getData> list;

    public RiwayatAdapter(Context context, ArrayList<getData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RiwayatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RiwayatAdapter.ViewHolder holder, int position) {
        getData data = list.get(position);
        holder.nama.setText("Nama: " + data.getNama());
        holder.alamat.setText("Alamat: " + data.getAlamat());
        holder.no_telp.setText("No. Telp: " + data.getNo_telp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, alamat, no_telp;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            alamat = itemView.findViewById(R.id.alamat);
            no_telp = itemView.findViewById(R.id.no_telp);
        }
    }
}
