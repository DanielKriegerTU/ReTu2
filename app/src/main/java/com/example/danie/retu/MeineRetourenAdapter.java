package com.example.danie.retu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
// ArrayAdapter zur Anzeige der List-View Items in der Klasse "Meine Retouren"
public class MeineRetourenAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //Textview Abgabeort
    private final List<String> ortArray;

    //Textview Abgabezeit
    private final List<String> zeitArray;

    //Textview ID
    private final List<String> idArray;


    public MeineRetourenAdapter(Activity context, List<String> ortArrayParam, List<String>  zeitArrayParam, List<String>  idArrayParam){

        super(context,R.layout.meine_reoturen_zeile , ortArrayParam);
        this.context=context;
        this.idArray = idArrayParam;
        this.ortArray =ortArrayParam;
        this.zeitArray= zeitArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.meine_reoturen_zeile, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView ortTextField = rowView.findViewById(R.id.AdapterAbgabeOrt);
        TextView zeitTextField = rowView.findViewById(R.id.AdapterAbgabeZeit);
        TextView idTextfield = rowView.findViewById(R.id.AdapterRetourenID);

        //this code sets the values of the objects to values from the arrays
        ortTextField.setText(ortArray.get(position));
        zeitTextField.setText(zeitArray.get(position));
        idTextfield.setText(idArray.get(position));

        return rowView;
    }
}
