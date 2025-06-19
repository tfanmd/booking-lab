package com.example.cobamysql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class add_data extends AppCompatActivity {

    Toolbar toolbar;
    TextView label;
    TextInputEditText nama, alamat, notelp;
    Button simpanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        simpanData = findViewById(R.id.simpanData);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        label = findViewById(R.id.label);

        if (getIntent().hasExtra("edit_data")) {
            label.setText("Edit Data");
            simpanData.setText("UBAH");
            GetData();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        simpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString().isEmpty()) {
                    nama.setError("Tidak boleh kosong");
                    return;
                }
                if (alamat.getText().toString().isEmpty()) {
                    alamat.setError("Tidak boleh kosong");
                    return;
                }
                if (notelp.getText().toString().isEmpty()) {
                    notelp.setError("Tidak boleh kosong");
                    return;
                }
                else {
                    String url = new Configurasi().baseUrl()+"public/simpan.php";

                    StringRequest stringRequest = new StringRequest(
                            1, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                if (status.equals("tersimpan") || status.equals("diubah")) {
                                    String pesan = status.equals("diubah") ? "Data berhasil diubah" : "Data berhasil disimpan";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(add_data.this);
                                    builder.setTitle("Sukses");
                                    builder.setMessage(pesan);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    builder.create().show();
                                } else {
                                    Toast.makeText(add_data.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(add_data.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> form = new HashMap<String, String>();
                            form.put("nama", nama.getText().toString());
                            form.put("alamat", alamat.getText().toString());
                            form.put("no_hp", notelp.getText().toString());
                            if (getIntent().hasExtra("edit_data")) {
                                form.put("id_mahasiswa", getIntent().getStringExtra("edit_data"));
                            }
                            return form;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    void GetData()
    {
        String url = new Configurasi().baseUrl()+"public/get_data.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                            String Vnama = jsonObject.getString("nama");
                            String Valamat = jsonObject.getString("alamat");
                            String Vno_hp = jsonObject.getString("no_hp");

                            nama.setText(Vnama);
                            alamat.setText(Valamat);
                            notelp.setText(Vno_hp);

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
                form.put("id_mahasiswa", getIntent().getStringExtra("edit_data"));
                return form;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }
}