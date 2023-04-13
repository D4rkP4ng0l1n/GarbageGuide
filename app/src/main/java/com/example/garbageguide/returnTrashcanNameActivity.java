package com.example.garbageguide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class returnTrashcanNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String ordure = intent.getStringExtra("ordure");

        SQLiteDatabase dbR = MainActivity.bdd.getWritableDatabase();

        Cursor cursSQL = dbR.rawQuery("select poubelle from Ordure where nom = '" + ordure + "'", null);

        String ordurePoubelle = null;

        if (cursSQL.moveToFirst()) {
            int nomIndex = cursSQL.getColumnIndexOrThrow("poubelle");
            ordurePoubelle = cursSQL.getString(nomIndex);
        }
        else{
            Toast.makeText(this, R.string.pas_de_reponse, Toast.LENGTH_SHORT).show();
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("poubelleBD", ordurePoubelle);

        setResult(returnTrashcanNameActivity.RESULT_OK, resultIntent);

        finish();

    }
}