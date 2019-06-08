package com.example.danie.retu;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private SeekBar seekbar_zeit;
    final LatLng dortmund_zimmerstrasse = new LatLng(51.521384, 7.463759);
    final LatLng dortmund_steinstrasse = new LatLng(51.520076, 7.457932);
    Marker zimmerstr ;
    Marker steinstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView zeitstrahl = (ImageView) findViewById(R.id.zeitstrahl);
        zeitstrahl.setImageResource(R.drawable.zeitstrahl);
        this.seekbar_zeit = findViewById(R.id.seekbar_zeit);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Veraendern der Marker durch Seekbar
        seekbar_zeit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 39) {
                    zimmerstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz));
                    zimmerstr.setTitle("Leider kein Platz verfügbar");
                }

                if (progress == 40) {
                  zimmerstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retugruen));
                  zimmerstr.setTitle("Super Platz verfügbar");
                }

                if (progress == 49) {
                    zimmerstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retugruen));
                    zimmerstr.setTitle("Super Platz verfügbar");
                }

                if (progress == 50) {

                    zimmerstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz));
                    zimmerstr.setTitle("Leider kein Platz verfügbar");

                    steinstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz));
                    steinstr.setTitle("Leider kein Platz verfügbar");
                }

                if (progress == 59) {
                    steinstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retugruen));
                    steinstr.setTitle("Super Platz verfügbar");
                }

                if (progress == 69) {
                    steinstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retugruen));
                    steinstr.setTitle("Super Platz verfügbar");
                }

                if (progress == 70) {
                    steinstr.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz));
                    steinstr.setTitle("Leider kein Platz verfügbar");
                }



                System.out.println(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }



    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng dortmund = new LatLng(51.51494, 7.466);
        LatLng dortmund_leopoldstr = new LatLng(51.521515, 7.462081);
        LatLng dortmund_steinstrasse1 = new LatLng(51.519948, 7.461722);
        LatLng dortmund_kapellenstrasse = new LatLng(51.519210, 7.465964);


        LatLng Berlin_Fern = new LatLng(52.521941, 13.404681);
        LatLng Berlin_Nikolai = new LatLng(52.517478, 13.407142);
        LatLng Berlin_Museum = new LatLng(52.519744, 13.398187);

        mMap.addMarker(new MarkerOptions().position(dortmund).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        mMap.addMarker(new MarkerOptions().position(dortmund_leopoldstr).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));

        mMap.addMarker(new MarkerOptions().position(dortmund_kapellenstrasse).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        mMap.addMarker(new MarkerOptions().position(dortmund_steinstrasse1).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        mMap.addMarker(new MarkerOptions().position(Berlin_Nikolai).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        mMap.addMarker(new MarkerOptions().position(Berlin_Fern).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        mMap.addMarker(new MarkerOptions().position(Berlin_Museum).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        //mMap.addMarker(new MarkerOptions().position(dortmund_zimmerstrasse).title("").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));

        zimmerstr = mMap.addMarker(new MarkerOptions().position(dortmund_zimmerstrasse).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));
        steinstr = mMap.addMarker(new MarkerOptions().position(dortmund_steinstrasse).title("Leider kein Platz verfügbar").icon(BitmapDescriptorFactory.fromResource(R.mipmap.retuschwarz)));

        mMap.setOnInfoWindowClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dortmund, 12f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }





    @Override
    public  void onInfoWindowClick(Marker marker){
        if(!marker.getTitle().equals("Leider kein Platz verfügbar") && marker.getPosition().equals(dortmund_steinstrasse)){

         String uhrzeit = "UHHR";
        Intent myIntent = new Intent(this, Uebersicht.class);
         myIntent.putExtra("Uhrzeit", uhrzeit);
        startActivity(myIntent);}


    }

}