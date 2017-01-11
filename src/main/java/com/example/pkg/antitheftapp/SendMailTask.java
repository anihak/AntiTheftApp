package com.example.pkg.antitheftapp;

/**
 * Created by LENOVO on 29/12/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendMailTask extends AsyncTask {

        private ProgressDialog statusDialog;
        private Activity sendMailActivity;
        String body="";
        String attach="";
        String Mail;
        String Pass;

        public SendMailTask(Activity activity, String mail,String pass,String mbody, String mAttach) {
            sendMailActivity = activity;
            body=mbody;
            attach=mAttach;
            Mail=mail;
            Pass=pass;


        }
    public SendMailTask() {

    }

        protected void onPreExecute() {
            //statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }




    @Override
        protected Object doInBackground(Object... args) {


                try {
                    GMailSender sender = new GMailSender(Mail, Pass);
                    sender.sendMail("Android Test",
                            body,
                            Mail,
                            Mail,attach);
                   // if (attach.equals("")) set attacheement
                    Log.i("SendMailTask", "mail sent ...");
                    Toast.makeText( sendMailActivity.getApplicationContext(), "in catch send gmail", Toast.LENGTH_SHORT ).show();


                } catch (Exception e) {
                   // Log.e("SendMailTask exception ", e.getMessage(), e);
                    //Toast.makeText( sendMailActivity.getApplicationContext(), "in catch send gmail", Toast.LENGTH_SHORT ).show();


                }





            return null;
        }

        @Override
        public void onProgressUpdate(Object... values) {
            statusDialog.setMessage(values[0].toString());

        }

        @Override
        public void onPostExecute(Object result) {
           statusDialog.dismiss();
        }

    }

