package com.example.danie.retu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Uebersicht extends AppCompatActivity {

    public RetourenEntity retoure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebersicht_angaben);


        retoure = new RetourenEntity();
        retoure.setRetoureID(MainMenu.retourenID);
        retoure.setAbgabeort("Kaiserviertel");
        retoure.setDatum("05.05.2019");
        retoure.setPaketgroesse("M");






    }

    public void OnClickUeberarbeiten(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);

    }

    public void OnClickBestaetigung(View view){
        //To-Do: Absichern dass Primary Key einmalig ist: und falls nicht insert verhindern.
       // MainMenu.datenbank.getRetourenDAO().deleteAll();
        MainMenu.datenbank.getRetourenDAO().insert(retoure);
        Intent myIntent = new Intent(this, MainMenu.class);
        Toast toast = Toast.makeText(this,"Es wurde ein Platz für Sie reserviert" + "", Toast.LENGTH_LONG );
        toast.show();
        startActivity(myIntent);
    }

}