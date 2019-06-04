package com.example.danie.retu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Uebersicht extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebersicht_angaben);

    }

    public void OnClickUeberarbeiten(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);

    }

    public void OnClickBestaetigung(View view){
        Intent myIntent = new Intent(this, MainMenu.class);
        Toast toast = Toast.makeText(this,"Es wurde ein Platz für Sie reserviert", Toast.LENGTH_LONG );
        toast.show();
        startActivity(myIntent);
    }

}