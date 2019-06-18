package com.example.fetare2kteam.fetare2k;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone_number);
        Button send_btn = (Button) findViewById(R.id.sendButton);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckPhoneNumberActivity.this,verificationcodeActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
