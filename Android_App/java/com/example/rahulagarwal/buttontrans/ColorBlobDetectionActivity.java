package com.example.rahulagarwal.buttontrans;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
//import org.opencv.samples.colorblobdetect.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.requireDeviceUnlock;
import static android.R.attr.value;
import static com.example.rahulagarwal.buttontrans.MainActivity.gcounter;
import static com.example.rahulagarwal.buttontrans.MainActivity.qrx2;
import static com.example.rahulagarwal.buttontrans.quadcheck.quadi2;
import static com.example.rahulagarwal.buttontrans.secondact.audiop;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ColorBlobDetectionActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG              = "OCVSample::Activity";
    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;
    private TextView textout;

    int reddetect = 0;
    int flag=0;
    static String qrc=" ";
    String qrca = " ";
    private CameraBridgeViewBase mOpenCvCameraView;
    static int xp=0;
    static int yp=0;
    static String book_name;
    static String page_name;
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorBlobDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.colorb);
       // book_name = getIntent().getExtras().getString("book");
       // page_name = getIntent().getExtras().getString("page");

        //if(getIntent().getExtras().getString("flag")!=null)
          //  flag=Integer.parseInt(getIntent().getExtras().getString("flag"));

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false; // don't need subsequent touch events
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mBlobColorRgba.val[0]=0;
        mBlobColorRgba.val[1]=0;
        mBlobColorRgba.val[2]=0;
        mBlobColorRgba.val[3]=255;
        mBlobColorHsv = new Scalar(240,255, 255, 0);

        mDetector.setHsvColor(mBlobColorHsv);//Red Color

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

        mIsColorSelected = true;
        if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            List<Moments> mu = new ArrayList<Moments>(contours.size());

            if(contours.size()!=0) {
                xp=0;
                yp=0;
                gcounter=0;
                reddetect =1;
                for (int i = 0; i < contours.size(); i++) {
                    mu.add(i, Imgproc.moments(contours.get(i), false));
                    Moments p = mu.get(i);
                    int x = (int) (p.get_m10() / p.get_m00());
                    int y = (int) (p.get_m01() / p.get_m00());
                    // Core.circle(mRgba, new Point(x, y), 4, new Scalar(255, 49, 0, 255));

                        xp=x;
                        yp=y;

                    //textout.setText("X Y : - "+x +y);
                }
            }
            else{
                int x = 5000;
                int y= 24000;
                try {

                   gcounter++;
                    if(gcounter==25) {
                        if(MainActivity.glocon == 0 && reddetect ==1)
                        {
                            MainActivity.qrx1 = xp;
                            MainActivity.qry1 = yp;
                            qrca = "1: "+ xp +  " "+ yp;
                            MainActivity.glocon++;
                            gcounter=0;
                            reddetect =0;

                            int hop = getResources().getIdentifier("check_one", "raw", getPackageName());
                            MediaPlayer playsound = MediaPlayer.create(this, hop);
                            playsound.start();
                            //Toast.makeText(getApplicationContext(), "1 tap", Toast.LENGTH_LONG).show();
                        }
                        else if(MainActivity.glocon==1 && reddetect==1) {
                            qrx2=xp;
                            MainActivity.qry2=yp;
                            qrca =qrca + " 2: "+ xp+ " " + yp;
                            MainActivity.glocon++;
                            gcounter=0;
                            reddetect=0;
                            int hop = getResources().getIdentifier("check_two", "raw", getPackageName());
                            MediaPlayer playsound = MediaPlayer.create(this, hop);
                            playsound.start();
                            //Toast.makeText(getApplicationContext(), "2 tap", Toast.LENGTH_LONG).show();
                        }
                        else if(MainActivity.glocon == 2 && reddetect ==1){
                            MainActivity.qrx3=xp;
                            MainActivity.qry3=yp;
                            qrca = qrca + " 3: " + xp + " " + yp;
                            MainActivity.glocon++;
                            gcounter=0;
                            reddetect =0;
                            int hop = getResources().getIdentifier("calibrated", "raw", getPackageName());
                            MediaPlayer playsound = MediaPlayer.create(this, hop);
                            playsound.start();
                            //Toast.makeText(getApplicationContext(), "3 tap", Toast.LENGTH_LONG).show();
                        }
                /*       if (glocon == 0 && reddetect == 1 && (MainActivity.new_flag==0)) {
                            qrx1 = xp;
                            qry1 = yp;
                           qrc = xp +  " "+ yp;
                            glocon++;
                           gcounter=0;
                           reddetect =0;
                           textout.setText("QR code detected");

                           //Toast.makeText(this, " first coordinate : " + xp +" " + yp, Toast.LENGTH_SHORT).show();
                        } else if (glocon == 1 && reddetect==1 && (MainActivity.new_flag==0)) {
                            qrx2 = xp;
                            qry2 = yp;
                           qrc = qrc +" "+ xp + " " + yp;
                            glocon++;
                           reddetect =0;
                           gcounter=0;
                           textout.setText("QR code Detected 2");

                           //Toast.makeText(getApplicationContext(), " second coordinate : " + xp +" " + yp, Toast.LENGTH_LONG).show();
                        }*/// else if (reddetect ==1){

                         else if(reddetect==1) {
                              gcounter = 0;
                              quadcheck q1 = new quadcheck();
                              quadcheck.quadi = 7;
                              qrca = qrca + " 4: " + xp + " " + yp;

                             int yfin = (int) getXcalib(xp, yp, MainActivity.qrx1, MainActivity.qry1, MainActivity.qrx2, MainActivity.qry2, MainActivity.qrx3, MainActivity.qry3);
                             int xfin = (int) getYcalib(xp, yp, MainActivity.qrx1, MainActivity.qry1, MainActivity.qrx2, MainActivity.qry2, MainActivity.qrx3, MainActivity.qry3);
                              //xfin = 29700 - xp * 29700/1024;
                              //yfin = yp * 21000/790;
                            //Toast.makeText(getApplicationContext(), "final tap", Toast.LENGTH_LONG).show();
                              qrca = qrca + "X: " + xfin + "Y: " + yfin + " ";
                              qrc = qrca;
                              if( (yfin<21745 && yfin >18745) &&(xfin <30614 && xfin>27614) ){
                                  MainActivity.qrx2=0;
                                  MainActivity.qrx1=0;
                                  MainActivity.qrx3=0;
                                  MainActivity.qry1=0;
                                  MainActivity.qry2=0;
                                  MainActivity.qry3=0;
                                  MainActivity.glocon=0;
                                  MainActivity.inputfile=null;
                                  Intent myInt = new Intent(ColorBlobDetectionActivity.this, MainActivity.class);
                                  ColorBlobDetectionActivity.this.startActivity(myInt);
                              }
                              else {
                                  q1.checkquad(yfin, xfin, this);
                                  quadi2 = quadi2 + "LOLZ";
                                  reddetect = 0;
                                  qrc = qrc + " . " + quadi2;
                                  MainActivity.new_flag = 1;
                                  // textout.setText("No.of Fingers : - " + gcounter);
                                  Intent myIntent = new Intent(ColorBlobDetectionActivity.this, audioplayer.class);
                                  myIntent.putExtra("book", book_name); //Optional parameters
                                  myIntent.putExtra("page", page_name); //Optional parameters
                                  //  myIntent.putExtra("flag_value",flag);
                                  ColorBlobDetectionActivity.this.startActivity(myIntent);
                              }
                          }
                    }
                }
                catch (Exception e)
                {
                    textout.setText("File1 Not Found, let's dance");
                }
                //  Core.circle(mRgba, new Point(x, y), 20, new Scalar(255,49,0,255));*/

            }

            Log.e(TAG, "Contours count: " + contours.size());
            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

        }
        else
        {
            int x =500;
            int y=500;
            //Core.circle(mRgba, new Point(x, y), 4, new Scalar(255,49,0,255));

        }

        return mRgba;
    }
    static double getXcalib(double x0, double y0, double x0qr, double y0qr, double x1qr, double y1qr, double x2qr, double y2qr){
        double x1 = x2qr-x1qr ;
        double x2 = x0qr-x1qr ;
        double y1 = y2qr-y1qr ;
        double y2 = y0qr-y1qr ;
        x0-=x1qr ;
        y0-=y1qr ;
        double denm = (x1*y2-x2*y1) ;
        return ((x0*y2-x2*y0)/denm)*19544 ;
    }
    static double getYcalib(double x0, double y0, double x0qr, double y0qr, double x1qr, double y1qr, double x2qr, double y2qr) {
        double x1 = x2qr-x1qr ;
        double x2 = x0qr-x1qr ;
        double y1 = y2qr-y1qr ;
        double y2 = y0qr-y1qr ;
        x0-=x1qr ;
        y0-=y1qr ;
        double denm = (x1*y2-x2*y1) ;
        return ((x1*y0-x0*y1)/denm)*28429 ;
    }
   /* private double getXcalib(double x0, double y0, double x0qr, double y0qr, double x1qr, double y1qr, double x2qr, double y2qr){
        double p2qr = 701.0;
        double q2qr = 680.0;
        double p1qr = 711.0;
        double q1qr = 29109.0;
        double p0qr = 20245.0;
        double q0qr = 29114.0;

        double xratio= (x2qr - x1qr)/(p2qr - p1qr);
        double yratio= (y2qr - y0qr)/(q2qr - q1qr);
        double newslope = (y2qr - y0qr)/(x2qr- x0qr);
        double theta = atan(newslope);
        double delta = atan(y0/x0);
        double a = x0/(cos(delta));
        double xnew = a * cos(delta - theta);
        double ynew = a * sin(delta - theta);
        double xfinal = -1 * xnew / xratio ;
        return xfinal;
    }*/
  /*  private double getYcalib(double x0, double y0, double x0qr, double y0qr, double x1qr, double y1qr, double x2qr, double y2qr) {
        double p2qr = 701.0;
        double q2qr = 680.0;
        double p1qr = 711.0;
        double q1qr = 29109.0;
        double p0qr = 20245.0;
        double q0qr = 29114.0;
        double xratio = (x2qr - x1qr) / (p2qr - p1qr);
        double yratio = (y2qr - y1qr) / (q0qr - q1qr);
        double newslope = (y2qr - y1qr) / (x2qr - x1qr);
        double theta = atan(newslope);
        double delta = atan(y0 / x0);
        double a = x0 / (cos(delta));
        double xnew = a * cos(delta - theta);
        double ynew = a * sin(delta - theta);
        double yfinal = ynew / yratio ;
        return yfinal;
    }*/

/*
    public class checker
    {
        public static void main ( String args [])
        {
            double x0, y0, x0qr, y0qr, x1qr, y1qr, x2qr, y2qr; //quantities obtained after scanning
            double p0qr, q0qr, p1qr, q1qr, p2qr, q2qr; // known quantities
            double xratio= (x2qr - x1qr)/(p2qr - p1qr);
            double yratio= (y1qr - y0qr)/(q1qr - q0qr);
            double newslope = (y2qr - y1qr)/(x2qr - x1qr);
            double theta = arctan(newslope);
            double delta = y0/x0;
            double a = x0/(cos(delta));
            double xnew = a * cos(delta - theta);
            double ynew = a * sin(delta - theta);
            double xfinal = xnew / xratio ;
            double yfinal = ynew / yratio ;

        }
    }
    */


    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
}