package com.example.rahulagarwal.buttontrans;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.detector.FinderPattern;

import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Scanner;

import static android.R.attr.button;
import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private Button menter,mrecord,mscanner,mscanner_page;
    static String inputfile,audiofile,inputfile1;
    private TextView currentb,currentt,currentp;
    static int gcounter = 0 ;
    static int new_flag=0;
    static int glocon = 0;
    static int qrx1=0,qry1=0,qrx2=0,qry2=0,qrx3=0,qry3=0;
    static String folder;
    static int qr_time = 0;
    static String locfile ;
    DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menter = (Button) findViewById(R.id.enter);
        mrecord = (Button) findViewById(R.id.record2);
        mscanner = (Button) findViewById(R.id.scanner);
        mscanner_page = (Button) findViewById(R.id.button);
        currentb = (TextView) findViewById(R.id.bookname);
        currentt = (TextView) findViewById(R.id.fingertap);
        currentp = (TextView) findViewById(R.id.pagename);
        glocon=0;
        final Activity activity = this;
        //set the book and page name
        currentb.setText("Current Book : "+folder);
        currentp.setText("Current Page : "+inputfile1);

        try{
            menter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse("http://www.cse.iitd.ac.in/~cs1150251/CV.pdf");

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"sample_data.pdf");
                    Long reference = downloadManager.enqueue(request);
                    Toast.makeText(MainActivity.this, "SAMPLE DATA DOWNLOADED", Toast.LENGTH_SHORT).show();
                }
            });


        }catch(Exception e)
        {
            Toast.makeText(MainActivity.this, "DOWNLOAD FAILED", Toast.LENGTH_SHORT).show();
        }


        mrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(folder!=null) {
                    if(inputfile!=null) {
                        Intent myIntent = new Intent(MainActivity.this, ColorBlobDetectionActivity.class);
                        MainActivity.this.startActivity(myIntent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "NO PAGE SELECTED", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "NO BOOK SELECTED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                qr_time = 0;
                MainActivity.glocon=0;
            }
        });
        mscanner_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                qr_time = 1;
                MainActivity.glocon=0;
            }
        });
    }
    protected void onActivityResult(int requestCode, int  resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {

                Toast.makeText(this, "YOU CANCELLED SCANNING", Toast.LENGTH_SHORT).show();
            }
            else
            {
                GetinputFile(result.getContents());
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    void GetinputFile(String a)
    {
        if(qr_time==0) {
            String tet = "";
            try {
                audioplayer text1 = new audioplayer();
                tet = text1.readinputfile("qrtable.txt");

            } catch (Exception e) {
            }
            currentb.setText(tet);
            Scanner s = new Scanner(tet);
            String k = s.next();
             k = s.next();
            try {
                while (true) {

                    if (k.compareTo(a) == 0) {
                        k = s.next();
                        break;
                    }
                    k = s.next();
                    k = s.next();
                }
                folder = k;
                currentb.setText("Current Book : " + k);
                qr_time=1;
            }
            catch(Exception e)
            {
                Toast.makeText(this, "INVALID QR CODE", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            String tet = "";
            String k="";
            Scanner s = new Scanner(tet);
            try {
                audioplayer text1 = new audioplayer();
                tet = text1.readinputfile(folder+"/qrtable.txt");
                 s = new Scanner(tet);
                 k = s.next();
                k=s.next();
            } catch (Exception e) {
                Toast.makeText(this, "FILE NOT FOUND", Toast.LENGTH_SHORT).show();
            }

          try {
              while (true) {

                  if (k.compareTo(a) == 0) {
                      k = s.next();
                      break;
                  }
                  k = s.next();
                  k = s.next();
              }
              inputfile = k;
              inputfile1 = k;
              currentp.setText("Current Page : "+inputfile);
              audioplayer tex1 = new audioplayer();
              locfile = folder + "/" + inputfile + "/";
              audiofile = tex1.readinputfile(locfile+"sounds.txt");
              audioplayer text11 = new audioplayer();
              inputfile = text11.readinputfile(locfile+"polygons.txt");
          }catch(Exception e)
          {
              Toast.makeText(this, "INVALID QR CODE", Toast.LENGTH_SHORT).show();
          }

        }
    }

}
