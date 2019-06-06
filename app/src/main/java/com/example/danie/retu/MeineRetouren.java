package com.example.danie.retu;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MeineRetouren extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_retouren);

        TextView anzeige = findViewById(R.id.datenbankanzeige);

      if(MainMenu.datenbank.getRetourenDAO().getAllRetouren() != null);

       List<RetourenEntity> retoureneintraege = MainMenu.datenbank.getRetourenDAO().getAllRetouren();

        String info = "";

        for(RetourenEntity re :retoureneintraege)
        {
            String id = re.getRetoureID();
            String datum = re.getDatum();
            String abgabe = re.getAbgabeort();
            info = info+id+datum+abgabe;
        }

        anzeige.setText(info);


}}

