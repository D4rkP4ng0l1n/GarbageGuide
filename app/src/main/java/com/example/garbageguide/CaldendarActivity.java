package com.example.garbageguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CaldendarActivity extends Activity {

    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Menu de navigation
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add:
                        Intent intentAdd = new Intent(CaldendarActivity.this, AddGarbageActivity.class);
                        startActivity(intentAdd);
                        return true;
                    case R.id.menu_home:
                        Intent intentHome = new Intent(CaldendarActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        bottomNavigationMenu.getMenu().getItem(2).setChecked(true);
    }
}
