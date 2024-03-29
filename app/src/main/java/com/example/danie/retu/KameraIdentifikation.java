package com.example.danie.retu;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

import static android.graphics.Color.LTGRAY;

// Redundante Logik zur File-Browser Identifikation: Siehe dazu Klasse "Idendifikation"
//Zusaetzliche Logik zur Identifikation mittels Kamera

public class KameraIdentifikation extends AppCompatActivity {
    Intent intent = getIntent();

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;


    //public String barcodeText = "";
    private static final int READ_REQUEST_CODE = 42;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    public ImageView myImageView;
    public Uri currentUri2;
    public BarcodeDetector detector;
    public String intentBarcode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retoure_identifizieren_kamera);
        surfaceView =(SurfaceView)findViewById(R.id.camerapreview);
        textView = (TextView)findViewById(R.id.txtContentzwei);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);

        // Button zu Beginn auf Gelb setzen und Hinweis im Text auf erforderliche Anmeldung
        Button weiter = findViewById(R.id.ButtonAnmeldenzwei);
        weiter.setText("Bitte verifizieren Sie Ihre Retoure\n um fortzufahren");
        weiter.setBackgroundColor(LTGRAY);
        weiter.setClickable(false);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        //Kamera initiieren
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(200, 200).build();

        // Surface-View zur Kamera Anzeige
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        // Logik zur QR-Code Erkennung
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }
            // Erkennen des QR-Codes erfolgreich
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if(qrCodes.size()!=0)
                {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            textView.setText(qrCodes.valueAt(0).displayValue);

                            //Ueberpruefen ob ID bereits in Datenbank ist
                            List<String> doppelID = MainMenu.datenbank.getRetourenDAO().findID(qrCodes.valueAt(0).rawValue);
                            System.out.println("Die groeße der Liste is  "+ doppelID.size());

                            // Anmeldung bei Doppelbuchung verhindern
                            if(doppelID.size() > 0)
                            //if(qrCodes.valueAt(0).rawValue.equalsIgnoreCase("1234567"))
                            {
                                String anzeige = "Retourennummer: " + qrCodes.valueAt(0).rawValue;
                                textView.setText(anzeige) ;
                                intentBarcode = qrCodes.valueAt(0).rawValue;
                                Button weiter = findViewById(R.id.ButtonAnmeldenzwei);
                                //sperrt den Knopf
                                weiter.setClickable(false);
                                weiter.setBackgroundColor(getResources().getColor(R.color.Rot));
                                weiter.setText("Anmeldung nicht möglich, \n Diese Retoure wurde bereits angemeldet");

                            }
                            else{
                                String anzeige = "Retourennummer: " + qrCodes.valueAt(0).rawValue + "\nIhre Sendung wurde als Retoure identifiziert";
                                textView.setText(anzeige);

                                // Id wird gespeichert und spaeter mit dem Intent an die naechste Activity uebergeben
                                intentBarcode = qrCodes.valueAt(0).rawValue;
                                Button weiter = findViewById(R.id.ButtonAnmeldenzwei);

                                //Freigabe Button
                                weiter.setClickable(true);
                                weiter.setBackgroundColor(getResources().getColor(R.color.ReTuGruen));
                                weiter.setText("Anmeldung möglich, \nWeiter zur Auswahl des Abgabeorts");
                            }


                        }
                    });
                }
            }
        });
    }

    public void OnClickAnmeldenzwei(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        myIntent.putExtra("ID", intentBarcode);
        startActivity(myIntent);
    }


    // Dokumentation siehe Klasse "Identifikation"
    public  void OnClickAusDateizwei(View view){
        performFileSearch();
    }

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

                    surfaceView.setVisibility(View.INVISIBLE);
                    currentUri2 = resultData.getData();
                    myImageView = findViewById(R.id.imgviewzwei);

                    myImageView.setImageBitmap(getBitmapFromUri(currentUri));
                    TextView txtView = (TextView) findViewById(R.id.txtContentzwei);
                    detector = new BarcodeDetector.Builder(getApplicationContext())
                                    .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                    .build();
                    if(!detector.isOperational()){
                        txtView.setText("Could not set up the detector!");
                        return;
                    }
                    System.out.println("Das ist die URI 2"+currentUri2 );
                    //
                    try { //Bitmap myBitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);

                        Bitmap myBitmap2 = getBitmapFromUri(currentUri2);
                        Frame frame = new Frame.Builder().setBitmap(myBitmap2).build();
                        SparseArray<Barcode> barcodes = detector.detect(frame);
                        Barcode thisCode = barcodes.valueAt(0);
                        String wert = thisCode.rawValue;
                        intentBarcode = thisCode.rawValue;
                        System.out.println(thisCode.rawValue);

                    //TODO Bereits gespeicherte Barcodes in Datenbnak erkennen und sperren.
                        List<String> doppelID = MainMenu.datenbank.getRetourenDAO().findID(thisCode.rawValue);
                        System.out.println("Die groeße der Liste is  "+ doppelID.size());
                        if(doppelID.size() > 0)
                        { txtView.setText("Retourennummer: " + thisCode.rawValue );
                            Button weiter = findViewById(R.id.ButtonAnmeldenzwei);
                            weiter.setClickable(false);
                            weiter.setBackgroundColor(getResources().getColor(R.color.Rot));
                            weiter.setText("Anmeldung nicht möglich, \n Diese Retoure wurde bereits angemeldet");

                        }
                        else{
                            txtView.setText("Retourennummer: " + thisCode.rawValue+ "\nIhre Sendung wurde als Retoure identifiziert" );
                            Button weiter = findViewById(R.id.ButtonAnmeldenzwei);
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


}