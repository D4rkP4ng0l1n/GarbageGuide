package com.example.garbageguide;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AddGarbageActivity extends Activity {

    private enum TrashColor { BLEU, JAUNE, VERT, NOIR, GRIS_ROUGE_MARRON };

    private RadioGroup typesDechets;
    private Button validateBtn;

    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garbage);

        typesDechets = findViewById(R.id.radiogroup_garbage_type);

        typesDechets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.isChecked()) {
                    int id = radioButton.getId();
                    String poubelle = null;

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

                    TextView affichageCouleur =findViewById(R.id.textView_trash_color_for_db);
                    affichageCouleur.setText(poubelle);
                }
            }
        });

        validateBtn = (Button) findViewById(R.id.validate_button_add_garbage);

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nomOrdure = findViewById(R.id.editTextTextPersonName);
                String nomOrdureString = nomOrdure.getText().toString();

                RadioGroup types = findViewById(R.id.radiogroup_garbage_type);
                int idType = types.getCheckedRadioButtonId();
                RadioButton selectedType = findViewById(idType);
                String selectedTypeText = selectedType.getText().toString();
                int selectedTypeId = selectedType.getId();
                String poubelle = null;

                switch(selectedTypeId) {
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

                ajouterOrdure(nomOrdureString, poubelle, selectedTypeText);
                Toast.makeText(getApplicationContext(), "Ordure " + nomOrdureString +  " ajoutée !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddGarbageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button quitBtn = (Button) findViewById(R.id.cancel_button_add_garbage);

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intentHome = new Intent(AddGarbageActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        return true;
                    case R.id.menu_calendar:
                        Intent intentCalendar = new Intent(AddGarbageActivity.this, CaldendarActivity.class);
                        startActivity(intentCalendar);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationMenu.getMenu().getItem(1).setChecked(true);
    }

    public void ajouterOrdure(String nom, String poubelle, String type) {
        SQLiteDatabase dbW = MainActivity.bdd.getWritableDatabase();
        dbW.execSQL("insert into Ordure values(null, '" + nom + "', '" + poubelle + "', '" + type + "');");
        dbW.close();
    }
}
