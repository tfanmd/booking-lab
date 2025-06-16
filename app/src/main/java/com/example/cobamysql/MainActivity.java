package com.example.cobamysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.cobamysql.Adapter.Adaptor;
import com.example.cobamysql.Adapter.getData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<getData> model;
    Adaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        loadData();
        adaptor = new Adaptor(this, model);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_opsi, popupMenu.getMenu()) ;
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.edit){
                            Toast.makeText(MainActivity.this, "You click edit", Toast.LENGTH_SHORT).show();
                        } else if (item.getItemId()==R.id.delete) {
                            Toast.makeText(MainActivity.this, "You click delete", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }
        });
    }

    void loadData(){
        model = new ArrayList<>();
        for(int i = 0; i<=30; i++){
            model.add(new getData("Taufan" +i,"","08978979087"));
        }
    }
}