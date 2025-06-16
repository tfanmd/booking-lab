package com.example.cobamysql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cobamysql.R;

import java.util.ArrayList;

public class Adaptor extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<getData> model;
    public Adaptor(Context context, ArrayList<getData> model){
        this.context = context;
        this.model = model;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView nama, alamat, no_telp;
        View view = inflater.inflate(R.layout.list_mahasiswa, null);
        if(view!=null){
            nama = view.findViewById(R.id.nama);
            alamat = view.findViewById(R.id.alamat);
            no_telp = view.findViewById(R.id.no_telp);

            nama.setText(model.get(position).getNama());
            alamat.setText(model.get(position).getAlamat());
            no_telp.setText(model.get(position).getNo_telp());
        }
        return view;
    }
}
