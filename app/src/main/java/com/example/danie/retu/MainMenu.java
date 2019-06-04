package com.example.danie.retu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        View retoureanmelden = findViewById(R.id.retoureanmelden);
        View meineretouren = findViewById(R.id.meineretouren);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);


    }
    public void OnClickRetoureAnmelden(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }



}
