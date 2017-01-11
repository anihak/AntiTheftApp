package com.example.pkg.antitheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TesterActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        Button cmd1 = (Button) findViewById(R.id.getLocCmd);
        cmd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), NotificationActivity.class);
                intent.putExtra("cmd",1);
                startActivity(intent);

            }
        });


        Button cmd2 = (Button) findViewById(R.id.TakeSnapCmd);
        cmd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(getApplicationContext(), NotificationActivity.class);
                intent.putExtra("cmd",2);
                startActivity(intent);
            }
        });
    }
}
