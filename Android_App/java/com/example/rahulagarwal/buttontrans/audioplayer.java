package com.example.rahulagarwal.buttontrans;

/**
 * Created by raj_rohit on 14/4/17.
 */

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static android.R.attr.value;
import static com.example.rahulagarwal.buttontrans.ColorBlobDetectionActivity.qrc;

public class audioplayer extends AppCompatActivity {
    MediaPlayer mPlayer;
    EditText get_text;
    FileInputStream fileInputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiopl);
        play_audio();

    }
    public String readinputfile(String fileName)
    {
        String completePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COP/" + fileName;
        String content = null;
        try {
            content = getFileContent(completePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
       /* TextView file_content = (TextView)findViewById(R.id.filename);
        if(file_content!=null)
            file_content.setText(content);
        else
            file_content.setText("file not read");*/
    }
    private String getFileContent(String targetFilePath) throws IOException {
        File file = new File(targetFilePath);
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        while(fileInputStream.available() > 0) {

            if(null== sb)  sb = new StringBuilder();

            sb.append((char)fileInputStream.read());
        }
        String fileContent = null;
        if(null!=sb){
            fileContent= sb.toString();
            // This is your fileContent in String.


        }
        try {
            fileInputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return fileContent;
    }

    public void goto_home(View v)
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        MainActivity.glocon=0;
//        myIntent.putExtra("book", book_naam); //Optional parameters
//        myIntent.putExtra("page", page_naam); //Optional parameters
        this.startActivity(myIntent);

    }

    public void start_audio(View v)
    {

        mPlayer.start();
        Toast.makeText(getApplicationContext(), "START", Toast.LENGTH_LONG).show();
    }

    public void pause_audio(View v)
    {
        if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.pause();
        }
        Toast.makeText(getApplicationContext(), "PAUSE", Toast.LENGTH_LONG).show();
    }

    public void stop_audio(View v)
    {
        if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.stop();
        }
        Toast.makeText(getApplicationContext(), "STOP", Toast.LENGTH_LONG).show();
    }

    public void play_audio()
    {

       // get_text = (EditText)findViewById(R.id.audio_name_play);
        //String get_name_of_string = get_text.getText().toString();
        final TextView display_text = (TextView) findViewById(R.id.display_text);
        if(secondact.audiop1.compareTo("apps")==0)
        {
            display_text.setText("");
            Toast.makeText(this, "NO AUDIO ASSOCIATED", Toast.LENGTH_SHORT).show();

            Intent myIntent2 = new Intent(audioplayer.this, ColorBlobDetectionActivity.class);
            myIntent2.putExtra("key", value); //Optional parameters
            audioplayer.this.startActivity(myIntent2);

        }
        else {
            // audio file can either have .mp3 or .wav format. This code will hndle both
            String fileName1 = secondact.audiop + ".mp3";
            String fileName2 = secondact.audiop + ".wav"; //check this format
            String completePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COP/" + MainActivity.locfile + fileName1;
            display_text.setText("");
            String newpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COP/" + MainActivity.locfile + fileName2;

            File file_mp3 = new File(completePath);
            File file_wav = new File(newpath);
            Uri myUri1 = null ;

            //set the correct uri
            if(file_mp3.exists()){
                /*play mp3 file*/
               myUri1 = Uri.fromFile(file_mp3);
            }
            else if(file_wav.exists())
            {
                /*play wav file*/
                myUri1 = Uri.fromFile(file_wav);
            }
            else
            {
                myUri1 = Uri.fromFile(file_wav);
            }

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.setDataSource(getApplicationContext(), myUri1);
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext(), "1 You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "2 You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "3 You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mPlayer.prepare();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "4 You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "5 You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            }

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    display_text.setText(" ");
                    Intent myIntent2 = new Intent(audioplayer.this, ColorBlobDetectionActivity.class);
                    myIntent2.putExtra("key", value); //Optional parameters
                    audioplayer.this.startActivity(myIntent2);
                }

            });

            mPlayer.start();
        }
    }

}
