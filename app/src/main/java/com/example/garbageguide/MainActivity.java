package com.example.garbageguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import BD.SQLOrdure;

public class MainActivity extends AppCompatActivity {

    private SQLOrdure bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Une instance de SQLOrdure suffit pour une activité et pour l'ensemble des tables...
        this.bdd = new SQLOrdure(this);

        Button addGarbage = (Button)findViewById(R.id.button_ajouterordure);

    }
}