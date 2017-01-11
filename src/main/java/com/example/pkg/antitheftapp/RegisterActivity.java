package com.example.pkg.antitheftapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {


    EditText EmailEdit;
    EditText PassEdit1;
    EditText PassEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);
        EmailEdit = (EditText)findViewById(R.id.regEmailEdit);
        PassEdit1 = (EditText)findViewById(R.id.regPassEdit1);
        PassEdit2 = (EditText)findViewById(R.id.regPassEdit2);








        Button btn = (Button) findViewById(R.id.btn_validate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempToRegister();
          }
        });

}



private void attempToRegister()
{
    String email=EmailEdit.getText().toString();
    String pass1 = PassEdit1.getText().toString();
    String pass2 = PassEdit1.getText().toString();

    View focusView = null;
    boolean cancel = false;


    if (!TextUtils.isEmpty(pass1) && !isPasswordValid(pass1)) {
        PassEdit1.setError(getString(R.string.error_invalid_password));
        focusView = PassEdit1;
        cancel = true;
    }else
    {if (!pass1.equals(pass2))
    { cancel=true;}}

    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
        EmailEdit.setError(getString(R.string.error_field_required));
        focusView = EmailEdit;
        cancel = true;
    } else if (!isEmailValid(email)) {
        EmailEdit.setError(getString(R.string.error_invalid_email));
        focusView = EmailEdit;
        cancel = true;
    }

    if (cancel == true)
    { Toast.makeText(getApplicationContext(), "Error!! the 2 values must be the same", Toast.LENGTH_LONG).show();
    }
    else
    { doRegister(email,pass1);
     }
}

private void doRegister(String email, String password) {
        SharedPreferences.Editor editor =  getSharedPreferences(ConfigData.MY_PREFS_NAME , MODE_PRIVATE).edit();
        editor.putString(ConfigData.EMAIL,email);
        editor.putString(ConfigData.PASSWORD,password);
        editor.putBoolean(ConfigData.REGISTRED,true);
        editor.putBoolean(ConfigData.REGISTRED, false);

        editor.commit();
        Intent intent = new Intent(getApplicationContext(), SetPinCodeActivity.class);
        startActivity(intent);

    }




    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



}
