package com.example.pkg.antitheftapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotificationActivity extends AppCompatActivity {

    Button btnShowLocation;
    TextView ltxt;
    GPSTracker gps;
    Camera camera;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    SendMailTask task;
    String path="";
    int cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
                Intent i = getIntent();
        cmd = i.getIntExtra("cmd",0);

        switch(cmd)
        {
            case(1):  String Result =getLocation();
                task= new SendMailTask(this,getMail(),getPass(),Result,"");
                task.execute();
                break;

            case(2) :
                String dir_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(); //"";// set your directory path here
                path =  dir_path + File.separator + "dcim" + "01" + ".jpg";
                takeSnapShots();
                Toast.makeText(getApplicationContext(),"sent file :"+ path  , Toast.LENGTH_LONG).show();

                task= new SendMailTask(this,getMail(),getPass(),"this is the taken photo ",path);
                task.execute();
                break;

            default:
                task= new SendMailTask(this,getMail(),getPass(),"error in cmd","");
                task.execute();

        }






        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public String getLocation()
    {// create class object
        String result="Error found";
        gps = new GPSTracker(NotificationActivity.this);
        // check if GPS enabled    
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            result = "the Location phone is - \nLat: " + latitude + "\nLong: " + longitude;
            // \n is for new line
            Toast.makeText(getApplicationContext(),result  , Toast.LENGTH_LONG).show();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }
        return result;
    }


    private void takeSnapShots() {
        Toast.makeText(getApplicationContext(), "Image snapshot   Started", Toast.LENGTH_SHORT).show();
        // here below "this" is activity context.
        SurfaceView surface = new SurfaceView(this);
         camera = Camera.open();
        try {
            camera.setPreviewDisplay(surface.getHolder());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        camera.startPreview();
        camera.takePicture(null, null, jpegCallback);
    }


    /**
     * picture call back
     */
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outStream = null;
            try {
                //String dir_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(); //"";// set your directory path here
                //path =  dir_path + File.separator + "dcim" + "01" + ".jpg";
                Toast.makeText(getApplicationContext(),"path ="+path   , Toast.LENGTH_LONG).show();

                outStream = new FileOutputStream(path);
                outStream.write(data);
                outStream.close();
                Log.d("Camera Error", "onPictureTaken - wrote bytes: " + data.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                camera.stopPreview();
                camera.release();
                camera = null;
                Toast.makeText(getApplicationContext(), "Image snapshot Done", Toast.LENGTH_LONG).show();


            }
            Log.d("Camera Error", "onPictureTaken - jpeg");
        }
    };


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("location Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private String getMail()
    {


        SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);
        String SavedMail = prefs.getString(ConfigData.EMAIL, null);
        return SavedMail;
    }

    private String getPass()
    {


        SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);
        String SavedPass = prefs.getString(ConfigData.PASSWORD, null);
        return SavedPass;
    }


}
