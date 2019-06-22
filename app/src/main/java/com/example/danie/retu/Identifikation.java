package com.example.danie.retu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

import static android.graphics.Color.LTGRAY;

// Activity zur Identifikation der Retouren, mittels File-Browser, (Kameraidentifikation in extra Activity)

public class Identifikation extends AppCompatActivity {
    Intent intent = getIntent();

    public String barcodeText = "";
    // Konstanten fuer File-Browser
    private static final int READ_REQUEST_CODE = 42;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    public ImageView myImageView;
    public Uri currentUri2;
    public  Bitmap myBitmap;
    public BarcodeDetector detector;
    public String intentBarcode = "";
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retoure_identifizieren);
        TextView txtView = (TextView) findViewById(R.id.txtContent);
        Button AusDatei = (Button) findViewById(R.id.ButtonAusDatei);
        ImageView myImageView = findViewById(R.id.imgview);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);

        // Button zu Beginn auf Grau setzen und Hinweis im Text auf erforderliche Anmeldung
        Button weiter = findViewById(R.id.ButtonAnmelden);
        weiter.setText("Bitte verifizieren Sie Ihre Retoure\n um fortzufahren");
        weiter.setBackgroundColor(LTGRAY);
        weiter.setClickable(false);

    }

    // Oeffnen des File-Browsers
    public void OnClickAusDatei(View view) {
        performFileSearch();
    }


    // Start der Activity Kameraidentifikation
    public  void OnClickKamera(View view){
        Intent myIntent = new Intent(this, KameraIdentifikation.class);
        startActivity(myIntent);
    }



    // File-Browser Methoden
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK)
        {


            if (requestCode == OPEN_REQUEST_CODE) {

                if (resultData != null) try {
                    System.out.println("Erfolgreich geladen");
                    currentUri = resultData.getData();
                    // Current URi 2 von mir eingefuegt

                    //Speichert den Link zum Aufruf des Bildes
                    currentUri2 = resultData.getData();
                    myImageView = findViewById(R.id.imgview);
                    // Laed das ausgewaelte Bild in den Image View
                    myImageView.setImageBitmap(getBitmapFromUri(currentUri));
                    TextView txtView = (TextView) findViewById(R.id.txtContent);
                    // Barcode detector wird erstellt
                    detector =
                            new BarcodeDetector.Builder(getApplicationContext())
                                    .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                    .build();
                    if(!detector.isOperational()){
                        txtView.setText("Could not set up the detector!");
                        return;
                    }
                    System.out.println("Das ist die URI 2"+currentUri2 );
                    //
                    try { //Bitmap myBitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);
                        // Liest QR-Code
                        Bitmap myBitmap2 = getBitmapFromUri(currentUri2);
                        Frame frame = new Frame.Builder().setBitmap(myBitmap2).build();
                        SparseArray<Barcode> barcodes = detector.detect(frame);
                        Barcode thisCode = barcodes.valueAt(0);
                        String wert = thisCode.rawValue;

                        //setzen des Strings zur weitergabe per Intent
                        intentBarcode = thisCode.rawValue;
                        System.out.println(thisCode.rawValue);

                        //Pruefen ob eine Retoure mit der selben Nummer bereits angemeldet wurde
                        List<String> doppelID = MainMenu.datenbank.getRetourenDAO().findID(thisCode.rawValue);
                        System.out.println("Die groeße der Liste is  "+ doppelID.size());

                        // In der Datenbank befindet sich bereits eine Retoure mit der selben Nummer
                        if(doppelID.size() > 0)
                        { txtView.setText("Retourennummer: " + thisCode.rawValue );
                            Button weiter = findViewById(R.id.ButtonAnmelden);
                            weiter.setClickable(false);
                            weiter.setBackgroundColor(getResources().getColor(R.color.Rot));
                            weiter.setText("Anmeldung nicht möglich, \n Diese Retoure wurde bereits angemeldet");
                        }
                        else{
                        txtView.setText("Retourennummer: " + thisCode.rawValue+ "\nIhre Sendung wurde als Retoure identifiziert" );
                        Button weiter = findViewById(R.id.ButtonAnmelden);
                        weiter.setClickable(true);
                        weiter.setBackgroundColor(getResources().getColor(R.color.ReTuGruen));
                            weiter.setText("Anmeldung möglich, \nWeiter zur Auswahl des Abgabeorts");
                        }

                    }

                    catch (IOException ie){

                        txtView.setText("Auslesen fehlgeschlagen");}



                }
                catch (IOException ie){
                    System.out.println("hi");


                }
            }
        }}


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void OnClickAnmelden(View view) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        // ID wird an naechste Activity uebergeben
        myIntent.putExtra("ID", intentBarcode);
        startActivity(myIntent);
    }





    }



