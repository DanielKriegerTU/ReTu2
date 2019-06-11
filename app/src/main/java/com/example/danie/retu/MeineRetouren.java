package com.example.danie.retu;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MeineRetouren extends AppCompatActivity {

    ListView listView;
    List<String>  ortArray = new ArrayList<>();
    List<String>  zeitArray = new ArrayList<>();
    List<String>  idArray =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_retouren);

        if(MainMenu.datenbank.getRetourenDAO().getAllRetouren() != null);{
        List<RetourenEntity> retoureneintraege = MainMenu.datenbank.getRetourenDAO().getAllRetouren();


        for( int i = 0; i< retoureneintraege.size(); i++) {
            ortArray.add(retoureneintraege.get(i).getAbgabeort());
            System.out.println(ortArray.get(i));

        }


        for(int i = 0; i< retoureneintraege.size(); i++) {
            zeitArray.add(retoureneintraege.get(i).getDatum()+" / "+retoureneintraege.get(i).getAbgabezeit());

        }



        for( int i = 0; i< retoureneintraege.size(); i++) {
            idArray.add(retoureneintraege.get(i).getRetoureID());

        }


        MeineRetourenAdapter adapter = new MeineRetourenAdapter(this, ortArray, zeitArray, idArray);

        listView = findViewById(R.id.listviewID);

        listView.setAdapter(adapter);

/*


       List<RetourenEntity> retoureneintraege = MainMenu.datenbank.getRetourenDAO().getAllRetouren();

        String info = "";

        for(RetourenEntity re :retoureneintraege)
        {
            String id = re.getRetoureID();
            String datum = re.getDatum();
            String abgabe = re.getAbgabeort();
            info = info+id+datum+abgabe;
        }

        anzeige.setText(info); */


}}}

