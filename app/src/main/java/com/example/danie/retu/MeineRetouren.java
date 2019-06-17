package com.example.danie.retu;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MeineRetouren extends AppCompatActivity {

    ListView listView;
    List<String>  ortArray = new ArrayList<>();
    List<String>  zeitArray = new ArrayList<>();
    List<String>  idArray =new ArrayList<>();

    //Delet
    ArrayAdapter<String> arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_retouren);

        if(MainMenu.datenbank.getRetourenDAO().getAllRetouren() != null);{
        final List<RetourenEntity> retoureneintraege = MainMenu.datenbank.getRetourenDAO().getAllRetouren();


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

        //NEUe loeschactivity

        final Intent intent = new Intent(this, MeineRetourenDetail.class );

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.putExtra("delID",(retoureneintraege.get(position).getRetoureID() ));
                    intent.putExtra("delOrt",retoureneintraege.get(position).getAbgabeort() );
                    intent.putExtra("delZeit", retoureneintraege.get(position).getAbgabezeit());
                    startActivity(intent);
                    finish();

                }
            });




        //Delete
            arrayadapter = new ArrayAdapter(MeineRetouren.this, android.R.layout.simple_list_item_activated_1, retoureneintraege);


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    // TODO Auto-generated method stub

                    MainMenu.datenbank.getRetourenDAO().deleteById(retoureneintraege.get(position).getRetoureID());
                    retoureneintraege.remove(position);


                    Toast.makeText(MeineRetouren.this, "Item Deleted", Toast.LENGTH_LONG).show();
                    arrayadapter.notifyDataSetChanged();
                    finish();
                    startActivity(getIntent());
                    return true;
                }

            });



}}}

