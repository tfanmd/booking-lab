package com.example.cobamysql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cobamysql.Adapter.Adaptor;
import com.example.cobamysql.Adapter.getData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<getData> model;
    Adaptor adaptor;
    Button tambahData;

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // ← Muat ulang data setiap kali activity muncul kembali
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        tambahData = findViewById(R.id.tambahData);
        tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), add_data.class);
                startActivity(intent);
            }
        });
        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position; // ← simpan posisi sebenarnya
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_opsi, popupMenu.getMenu()) ;
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.edit){
                            Intent intent = new Intent(getApplicationContext(), add_data.class);
                            intent.putExtra("edit_data", model.get(position).getId_mahasiswa()); // ganti i jadi position
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Anda yakin ingin menghapus data ini?");
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    _hapus(model.get(pos).getId_mahasiswa()); // gunakan pos, bukan which
                                }
                            });
                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        return false;
                    }
                });
            }
        });
    }

    void _hapus(String id_mahasiswa){
        String url = new Configurasi().baseUrl()+"public/hapus.php";
        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("hapus"))
                            {
                                Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                loadData();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> form = new HashMap<String, String>();
                form.put("id_mahasiswa", id_mahasiswa);
                return form;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    String getInisial(String nama) {
        if (nama != null && nama.length() > 0) {
            return String.valueOf(nama.charAt(0)).toUpperCase(); // huruf besar
        } else {
            return "?";
        }
    }

    void loadData(){
        String url = new Configurasi().baseUrl()+"public/tampil_data.php";

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            model = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String id = getData.getString("id_mahasiswa");
                                String nama = getData.getString("nama");
                                String alamat = getData.getString("alamat");
                                String no_hp = getData.getString("no_hp");
                                String inisial = getInisial(nama);

                                model.add(new getData(
                                        inisial,
                                        id,
                                        nama,
                                        alamat,
                                        no_hp
                                ));
                            }

                            // 🔽 Tambahkan ini untuk sorting berdasarkan nama (atau inisial)
                            java.util.Collections.sort(model, new java.util.Comparator<getData>() {
                                @Override
                                public int compare(getData o1, getData o2) {
                                    return o1.getNama().compareToIgnoreCase(o2.getNama());
                                }
                            });

                            adaptor = new Adaptor(MainActivity.this, model);
                            listView.setAdapter(adaptor);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}