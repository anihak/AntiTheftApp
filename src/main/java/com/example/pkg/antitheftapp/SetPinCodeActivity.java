package com.example.pkg.antitheftapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetPinCodeActivity extends AppCompatActivity {
EditText pin1,pin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button btn = (Button) findViewById(R.id.btn_validate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);

                pin1 = (EditText)findViewById(R.id.pinCode1);
                pin2 = (EditText)findViewById(R.id.pinCode2);

                if (pin1.getText().toString().equals((pin2.getText().toString())))
                {

                    SharedPreferences.Editor editor =  getSharedPreferences(ConfigData.MY_PREFS_NAME , MODE_PRIVATE).edit();
                    editor.putString(ConfigData.PINCODE, pin1.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);

                }
                else
                {
                    pin1.setText("");
                    pin2.setText("");
                    Toast.makeText(getApplicationContext(), "Error!! the 2 values must be the same", Toast.LENGTH_LONG).show();
                }

            }
        });


            //Toast.makeText(getApplicationContext(), Email, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), Password, Toast.LENGTH_LONG).show();





    }




}
