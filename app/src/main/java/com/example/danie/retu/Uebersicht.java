package com.example.danie.retu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Uebersicht extends AppCompatActivity {

    public RetourenEntity retoure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebersicht_angaben);

        //String aus Activity Maps
        String Uhrzeit;
        String Datum;
        String Paketgroesse;
        String Abgabeort;
        int MapId;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Uhrzeit= null;
                Datum =null;
                Paketgroesse = null;
                Abgabeort =null;
                MapId = R.drawable.map_zimmerstrasse;

            } else {
                Uhrzeit= extras.getString("Uhrzeit");
                Datum = extras.getString("Datum");
                Paketgroesse = extras.getString("Paketgroesse");
                Abgabeort = extras.getString("Abgabeort");
                MapId = extras.getInt("MapId");
            }
        } else {
            Uhrzeit= (String) savedInstanceState.getSerializable("Uhrzeit");
            Datum= (String) savedInstanceState.getSerializable("Datum");
            Paketgroesse= (String) savedInstanceState.getSerializable("Paketgroess");
            Abgabeort= (String) savedInstanceState.getSerializable("Abgabeort");
            MapId = (int) savedInstanceState.getSerializable("MapId");

        }


        //Datenbank ------------------------------------------------------------------
        retoure = new RetourenEntity();
        retoure.setRetoureID(MainMenu.retourenID);
        retoure.setAbgabeort(Abgabeort);
        retoure.setDatum(Datum);
        retoure.setPaketgroesse(Paketgroesse);
//---------------------------------------------------------------------------------------------

        TextView Vdatum = findViewById(R.id.Datum);
        Vdatum.setText(Datum);

        TextView Vuhrzeit = findViewById(R.id.uhrzeit);
        Vuhrzeit.setText(Uhrzeit);

        TextView Vpaketgroesse = findViewById(R.id.paketgroesse);
        Vpaketgroesse.setText(Paketgroesse);

        TextView VAbgabeort = findViewById(R.id.abgabeort);
        VAbgabeort.setText(Abgabeort);

        Drawable DMapId = getResources().getDrawable(MapId) ;
        ImageView Vimage = findViewById(R.id.mapbild);
        Vimage.setImageDrawable(DMapId);





    }

    public void OnClickUeberarbeiten(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);

    }

    public void OnClickBestaetigung(View view){
        //To-Do: Absichern dass Primary Key einmalig ist: und falls nicht insert verhindern.
        MainMenu.datenbank.getRetourenDAO().deleteById(retoure.getRetoureID());
        MainMenu.datenbank.getRetourenDAO().insert(retoure);
        Intent myIntent = new Intent(this, MainMenu.class);
        Toast toast = Toast.makeText(this,"Es wurde ein Platz für Sie reserviert" + "", Toast.LENGTH_LONG );
        toast.show();
        startActivity(myIntent);
    }

}