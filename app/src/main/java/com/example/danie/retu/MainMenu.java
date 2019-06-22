package com.example.danie.retu;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// Hauptmenue wird bei Start der App aufgerufen
public class MainMenu extends AppCompatActivity {


    public static RetourenDatenbank datenbank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        View retoureanmelden = findViewById(R.id.retoureanmelden);
        View meineretouren = findViewById(R.id.meineretouren);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);



        // Intiierung der Datenbank
        datenbank = Room.databaseBuilder(getApplicationContext(), RetourenDatenbank.class, "aufgegebene Retouren")
                .allowMainThreadQueries()
                .build();


    }
    // Intent zum Start der Identifikation
    public void OnClickRetoureAnmelden(View view){
        Intent myIntent = new Intent(this, Identifikation.class);
        //myIntent.putExtra("ReteourenID", retourenID);
        startActivity(myIntent);
    }

    // Intent zum Start MeineRetouren
    public void OnClickMeineRetouren(View view){
        Intent myIntent = new Intent(this, MeineRetouren.class);
        startActivity(myIntent);
    }



}
