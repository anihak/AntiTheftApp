package com.example.pkg.antitheftapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

   private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world" };
    //Keep track of the login task to ensure we can cancel it if requested.
    //private LogActivity.UserLoginTask mAuthTask = null;
  // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private TextView Statut;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        if(ContextCompat.checkSelfPermission( getBaseContext(), "android.permission.READ_SMS")== PackageManager.PERMISSION_GRANTED);
        else

            ActivityCompat.requestPermissions(LogActivity.this,new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);


        mEmailView = (AutoCompleteTextView)findViewById(R.id.logEmailEdit);
        mPasswordView = (EditText)findViewById(R.id.logPassEdit);
        Statut = (TextView) findViewById(R.id.LogRegStatut);
        Button logLoginBtn = (Button) findViewById(R.id.logLoginBtn);
        logLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
                // retrieve data and check if pass and login are valid
            }});

        Button logRegisterBtn = (Button)findViewById(R.id.logRegisterBtn);
        logRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                //pass email to register activity
        }});

        if (isRegistred())
        { logRegisterBtn.setClickable(false);
            Statut.setText("");
        }
        else
         {logLoginBtn.setClickable(false);
             Statut.setText("You have to Register to activate the application");
          }


        //kahina
        Button btn = (Button)findViewById(R.id.getLocCmd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  TesterActivity.class);
                startActivity(intent);


            }
        });
        //kahina
    }


  private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
   // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if ( !CheckIdentifiant(email, password)) cancel= true;
       if (cancel) {
            // form field with an error.
          // focusView.requestFocus();
            Toast.makeText(getApplicationContext(), "Authentification error , you can't login ", Toast.LENGTH_LONG).show();
        } else {
            //showProgress(true);
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);



        }
    }

    private boolean isRegistred() {

        SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);
        boolean registred = prefs.getBoolean(ConfigData.REGISTRED, false);

        return registred;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private boolean CheckIdentifiant(String mail, String password)
    {
         boolean check=false;

        SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);
        String SavedMail = prefs.getString(ConfigData.EMAIL, null);
        String SavedPass = prefs.getString(ConfigData.PASSWORD, null);


        if (SavedMail != null && SavedPass!=null) {
            if (SavedMail.equals(mail) && SavedPass.equals(password) )
                check=true;
        }

        return check;}








}
