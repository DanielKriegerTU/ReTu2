package com.example.danie.retu;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainMenu extends AppCompatActivity {


    public static RetourenDatenbank datenbank;
    // Vergibt einmalige Retouren ID --> muss spaeter in die Anmeldung ueberommen werden
    public static String retourenID ="Hi";
    public int erhoehungId =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        View retoureanmelden = findViewById(R.id.retoureanmelden);
        View meineretouren = findViewById(R.id.meineretouren);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);



        datenbank = Room.databaseBuilder(getApplicationContext(), RetourenDatenbank.class, "aufgegebene Retouren")
                .allowMainThreadQueries()
                .build();


    }
    public void OnClickRetoureAnmelden(View view){

        // Einalige Id wird generiert und spaeter in der Klasse Ubersicht abgerugen um dann an die Datenbank zu uebergeben.
        erhoehungId = erhoehungId+1;
        retourenID = retourenID+erhoehungId;
        Intent myIntent = new Intent(this, MapsActivity.class);
        //myIntent.putExtra("ReteourenID", retourenID);
        startActivity(myIntent);
    }

    public void OnClickMeineRetouren(View view){
        Intent myIntent = new Intent(this, MeineRetouren.class);
        startActivity(myIntent);
    }



}
