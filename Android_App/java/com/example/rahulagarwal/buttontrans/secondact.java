package com.example.rahulagarwal.buttontrans;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.value;

/**
 * Created by RAHUL AGARWAL on 11-04-2017.
 */

public class secondact extends AppCompatActivity {
    private TextView mquad;
    static String audiop,audiop1;
    public void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        mquad = (TextView) findViewById(R.id.textView);
        //mquad.setText("Quad :-  "+quadcheck.quadi+"Audio :- "+audiop+" lp "+ColorBlobDetectionActivity.qrx1+"K"+ColorBlobDetectionActivity.qry1+"LL"+ColorBlobDetectionActivity.qrx2+"K"+ColorBlobDetectionActivity.qry2);
        try {
            //audioplayer aud = new audioplayer();
            //aud.play_audio(audiop);
           /*int hop = getResources().getIdentifier(audiop, "raw", getPackageName());
            MediaPlayer playsound = MediaPlayer.create(this, hop);
            playsound.start();
            long aud = playsound.getDuration();
            SystemClock.sleep(aud + 2000);*/
            //SystemClock.sleep(3000);
            Intent myIntent2 = new Intent(secondact.this, audioplayer.class);
            myIntent2.putExtra("key", value); //Optional parameters
            secondact.this.startActivity(myIntent2);
        }

        catch (Exception e)
        {
           // mquad.setText("Quad :-  "+quadcheck.quadi+"Audio :- "+audiop+" lp "+ColorBlobDetectionActivity.qrx1+"K"+ColorBlobDetectionActivity.qry1+"LL"+ColorBlobDetectionActivity.qrx2+"K"+ColorBlobDetectionActivity.qry2+"llk"+audioplayer.aab);
            mquad.append("Sound could not play");
        }
    }
}
