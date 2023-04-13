package com.example.garbageguide;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class InfoGarbageActivity extends Activity {

    private String poubelle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_garbage);
        TextView ordure = findViewById(R.id.nomOrdure);
        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("ordure");
        ordure.setText(selectedItem);

        Button btnRetour = findViewById(R.id.button_retour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btnSupp = findViewById(R.id.button_delete);
        btnSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dbW = MainActivity.bdd.getWritableDatabase();
                dbW.execSQL("DELETE FROM Ordure WHERE nom = '" + selectedItem + "'");
                dbW.close();

                onBackPressed();
            }
        });

        RadioGroup typesDechets = findViewById(R.id.radiogroup_garbage_type);

        typesDechets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.isChecked()) {
                    int id = radioButton.getId();

                    switch(id) {
                        case R.id.radioButton_blue_trash:
                            poubelle = "Bleue";
                            break;
                        case R.id.radioButton_yellow_trash:
                            poubelle = "Jaune";
                            break;
                        case R.id.radioButton_green_trash:
                            poubelle = "Verte";
                            break;
                        case R.id.radioButton_black_trash:
                            poubelle = "Noire";
                            break;
                        case R.id.radioButton_grey_red_brown_trash:
                            poubelle = "Grise, Rouge ou Marron";
                            break;
                    }
                }
            }
        });

        Button btnValider = findViewById(R.id.button_validate);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoGarbageActivity.this, returnTrashcanNameActivity.class);
                intent.putExtra("ordure", selectedItem);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String poubelleBD = data.getStringExtra("poubelleBD");
            if (poubelleBD.equals(poubelle)) {
                Toast.makeText(this, R.string.toast_goodTrash, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.toast_badTrash), Toast.LENGTH_SHORT).show();
            }
        }
    }
}