package com.example.cobamysql;

import  android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cobamysql.Adapter.getData;
import com.example.cobamysql.Adapter.RiwayatAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RiwayatBooking extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<getData> list;
    RiwayatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_booking);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new RiwayatAdapter(this, list);
        recyclerView.setAdapter(adapter);

        loadData(); // baru load data setelah semuanya siap
    }


    void loadData() {
        list.clear(); // clear dulu sebelum isi ulang
        String url = new Configurasi().baseUrl() + "public/tampil_data.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            list.clear(); // penting
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getDataObj = jsonArray.getJSONObject(i);
                                String id = getDataObj.getString("id_mahasiswa");
                                String nama = getDataObj.getString("nama");
                                String alamat = getDataObj.getString("alamat");
                                String no_hp = getDataObj.getString("no_hp");
                                String inisial = getInisial(nama);

                                list.add(new getData(inisial, id, nama, alamat, no_hp));
                            }

                            adapter.notifyDataSetChanged(); // WAJIB
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RiwayatBooking.this, "Gagal parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RiwayatBooking.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    String getInisial(String nama) {
        if (nama != null && nama.length() > 0) {
            return String.valueOf(nama.charAt(0)).toUpperCase();
        } else {
            return "?";
        }
    }
}
