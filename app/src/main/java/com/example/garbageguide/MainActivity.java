package com.example.garbageguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import BD.SQLOrdure;

public class MainActivity extends AppCompatActivity {

    public static SQLOrdure bdd;

    private Button addGarbage;
    private ListView garbageList;
    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Une instance de SQLOrdure suffit pour une activité et pour l'ensemble des tables...
        bdd = new SQLOrdure(this);

        // Ajouter une nouvelle ordure
        addGarbage = (Button) findViewById(R.id.button_ajouterordure);
        addGarbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGarbageActivity.class);
                startActivity(intent);
            }
        });

        // Obtenir les infos d'une ordure
        garbageList = (ListView) findViewById(R.id.list_ordures);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.ordure_in_list_layout, android.R.id.text1);
        garbageList.setAdapter(adapter);

        ArrayList<String> ordures = new ArrayList<>();
        SQLiteDatabase dbR = bdd.getWritableDatabase();

        Cursor cursSQL = dbR.rawQuery("select nom, type from Ordure", null);

        if (cursSQL.moveToFirst()) {
            int nomIndex = cursSQL.getColumnIndexOrThrow("nom");
            int typeIndex = cursSQL.getColumnIndexOrThrow("type");

            do {
                String ordureNOM = cursSQL.getString(nomIndex);
                String ordureTYPE = cursSQL.getString(typeIndex);

                ordures.add(ordureNOM + " - " + ordureTYPE);
            } while (cursSQL.moveToNext());
        }
        else{
            Toast.makeText(this, R.string.no_response, Toast.LENGTH_SHORT).show();
        }
        adapter.addAll(ordures);

        // Menu de navigation
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add:
                        Intent intentAdd = new Intent(MainActivity.this, AddGarbageActivity.class);
                        startActivity(intentAdd);
                        return true;
                    case R.id.menu_calendar:
                        Intent intentCalendar = new Intent(MainActivity.this, CaldendarActivity.class);
                        startActivity(intentCalendar);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.hidden_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.exit(0);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationMenu.getMenu().getItem(0).setChecked(true);
    }
}