package com.example.danie.retu;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

//Angedachtes personalisiertes Info Window fuer die Anzeige der Verfuegbaren Plaetze in der Map,
// Wurde noch nicht in die MapsActivity uebernommen.

public class CustomInfoWindowReTu implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowReTu(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custominfowindow, null);

        TextView infoWindotext = view.findViewById(R.id.infowindowtext);
        infoWindotext.setText(marker.getTitle());



        return view;

}
}
