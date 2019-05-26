package com.example.danie.retu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }
    public void OnClickRetoureAnmelden(View view){
        Intent myIntent = new Intent(this, PaketGroesse.class);
        startActivity(myIntent);
    }



}
