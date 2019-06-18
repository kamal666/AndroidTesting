package com.example.fetare2kteam.fetare2k;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                Intent i = new Intent(LogoActivity.this,UserTypeActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}
