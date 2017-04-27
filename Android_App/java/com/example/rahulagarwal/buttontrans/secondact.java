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


public class secondact extends AppCompatActivity {
    private TextView mquad;
    static String audiop,audiop1;
    public void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        mquad = (TextView) findViewById(R.id.textView);
        try {
           Intent myIntent2 = new Intent(secondact.this, audioplayer.class);
            myIntent2.putExtra("key", value); //Optional parameters
            secondact.this.startActivity(myIntent2);
        }

        catch (Exception e)
        {
           mquad.append("Sound could not play");
        }
    }
}
