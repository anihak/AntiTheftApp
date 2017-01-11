package com.example.pkg.antitheftapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetPasswordActivity extends AppCompatActivity {
    private Button btn;
    private EditText pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



         btn = (Button) findViewById(R.id.btn_validate_pass);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(ConfigData.MY_PREFS_NAME, MODE_PRIVATE);

                pass1 = (EditText)findViewById(R.id.cpass1);
                pass2 = (EditText)findViewById(R.id.cpass2);

                if (pass1.getText().toString().equals((pass2.getText().toString())))
                {
                    SharedPreferences.Editor editor =  getSharedPreferences(ConfigData.MY_PREFS_NAME , MODE_PRIVATE).edit();

                    editor.putString(ConfigData.PASSWORD, pass1.getText().toString());
                    editor.commit();
                    finish();

                }
                else
                {
                    pass1.setText("");
                    pass2.setText("");
                    Toast.makeText(getApplicationContext(), "Error!! the 2 values must be the same", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
