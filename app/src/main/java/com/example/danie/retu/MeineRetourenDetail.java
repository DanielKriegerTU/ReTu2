package com.example.danie.retu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//Detailansicht der Retoure, wird bei Click auf Listview-Item angezeigt
// ermoeglicht es auch Retouren zu stornieren.
public class MeineRetourenDetail extends AppCompatActivity {

    String delID = null ;
    String delOrt =null;
    String delZeit = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_retouren_detail);
         //Intent-Informationen ueber die geklickte Retoure aus der Acitvity "MeineRetouren"
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                delID = null;
                delOrt =null;
                delZeit = null;

            } else {

                delID = extras.getString("delID");
                delOrt = extras.getString("delOrt");
                delZeit =extras.getString("delZeit");
            }
        } else {

            delID = (String) savedInstanceState.getSerializable("delID");
            delZeit = (String) savedInstanceState.getSerializable("delZeit");
            delOrt = (String) savedInstanceState.getSerializable("delOrt");

        }

        TextView Anzeige = findViewById(R.id.detailID);
        View storno = findViewById(R.id.detailStornieren);

        Anzeige.setText("Abgabeort: " + delOrt + "\n\nAbgabezeit: 20.06.19 / " +delZeit + "\n\nRetouren ID: "+ delID );

    }

    // Stornieren der Retoure, --> Loeschen der Retoure aus der Datenbank
    public void OnClickStorno(View view) {
        Toast.makeText(MeineRetourenDetail.this, "Ihre Retoure wurde erfolgreich storniert", Toast.LENGTH_LONG).show();
        MainMenu.datenbank.getRetourenDAO().deleteById(delID);
        final Intent intent = new Intent(this, MeineRetouren.class );
        startActivity(intent);
        finish();

    }

    }




