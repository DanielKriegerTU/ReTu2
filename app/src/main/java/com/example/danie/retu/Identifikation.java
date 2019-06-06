package com.example.danie.retu;

import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.FileDescriptor;
import java.io.IOException;

public class Identifikation extends AppCompatActivity {
    public String barcodeText = "";
    private static final int READ_REQUEST_CODE = 42;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    public ImageView myImageView;
    public Uri currentUri2;
    public  Bitmap myBitmap;
    public BarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retoure_identifizieren);
        TextView txtView = (TextView) findViewById(R.id.txtContent);
        Button AusDatei = (Button) findViewById(R.id.ButtonAusDatei);
       // Button Identifizieren = (Button) findViewById(R.id.Buttonidentifizieren);
        ImageView myImageView = findViewById(R.id.imgview);

        //ImageView myImageView = (ImageView) findViewById(R.id.imgview);
        //Bitmap myBitmap = BitmapFactory.decodeResource(
               // getApplicationContext().getResources(),
                // R.drawable.puppy);
        //myImageView.setImageBitmap(myBitmap);




       // Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        // SparseArray<Barcode> barcodes = detector.detect(frame);

       // final Barcode thisCode = barcodes.valueAt(0);
        // barcodeText = thisCode.rawValue;

}

    public void OnClickAusDatei(View view) {

        performFileSearch();
    }

    public void OnClickKamera(View view) {

        Intent myIntent = new Intent(this, PaketGroesse.class);
        startActivity(myIntent);
    }


    public void OnClickAnmelden(View view) {

        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }


    //OnClick identifizieren wurde in OnActivityResult uebernommen
   /* public void OnClickIdentifizieren(View view){
        TextView txtView = (TextView) findViewById(R.id.txtContent);
        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            txtView.setText("Could not set up the detector!");
            return;
        }
            System.out.println("DAs ist die URI 2"+currentUri2 );
        //
        try { //Bitmap myBitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);

            Bitmap myBitmap2 = getBitmapFromUri(currentUri2);
            Frame frame = new Frame.Builder().setBitmap(myBitmap2).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            Barcode thisCode = barcodes.valueAt(0);

            txtView.setText("Retourennummer: " + thisCode.rawValue); }

          catch (IOException ie){

                txtView.setText("Auslesen fehlgeschlagen");}


            //Toast toast = Toast.makeText(this, barcodeText , Toast.LENGTH_LONG );
            //toast.show();
            //Intent myIntent = new Intent(this, MapsActivity.class);
            //startActivity(myIntent);
        } */




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
                    currentUri2 = resultData.getData();
                    myImageView = findViewById(R.id.imgview);

                    myImageView.setImageBitmap(getBitmapFromUri(currentUri));
                    TextView txtView = (TextView) findViewById(R.id.txtContent);
                    detector =
                            new BarcodeDetector.Builder(getApplicationContext())
                                    .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                    .build();
                    if(!detector.isOperational()){
                        txtView.setText("Could not set up the detector!");
                        return;
                    }
                    System.out.println("DAs ist die URI 2"+currentUri2 );
                    //
                    try { //Bitmap myBitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);

                        Bitmap myBitmap2 = getBitmapFromUri(currentUri2);
                        Frame frame = new Frame.Builder().setBitmap(myBitmap2).build();
                        SparseArray<Barcode> barcodes = detector.detect(frame);
                        Barcode thisCode = barcodes.valueAt(0);

                        txtView.setText("Retourennummer: " + thisCode.rawValue+ "\nIhre Sendung wurde als Retoure identifiziert" );
                        Button weiter = findViewById(R.id.ButtonAnmelden);
                        weiter.setBackgroundColor(getResources().getColor(R.color.ReTuGruen));

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
