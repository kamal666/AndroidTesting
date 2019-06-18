package com.example.fetare2kteam.fetare2k;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInCaptinActivity extends AppCompatActivity {
    EditText EditText_phone;
    TextView signUpNow_textView;
    Button login_button;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_captin);
        login_button = (Button) findViewById(R.id.logIn_btn);
         signUpNow_textView = (TextView) findViewById(R.id.signUpNow_btn);
        EditText_phone=findViewById(R.id.EditText_phone);
        auth=FirebaseAuth.getInstance();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToHomePage();
            }
        });

        signUpNow_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpPage();
            }
        });


    }

    public void goToHomePage()
    {
        String phone=EditText_phone.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(LogInCaptinActivity.this, "Enter PhoneNumber", Toast.LENGTH_SHORT).show();
        }else {
           /* auth.signInWithEmailAndPassword(phone,txt_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(LogInCaptinActivity.this,captain_home_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LogInCaptinActivity.this, "Authentication  failed!", Toast.LENGTH_SHORT).show();
                            }}
                    });*/
           /* DocumentReference docRef = db.collection("Captains").document();
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                        } else {

                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });*/
            Intent i = new Intent(LogInCaptinActivity.this,LoginVerificationcodeActivity.class);
            i.putExtra("phone",phone);
            startActivity(i);

        }
    }
    public void goToCheckPhoneNumberPage()
    {
        Intent i = new Intent(LogInCaptinActivity.this,CheckPhoneNumberActivity.class);
        startActivity(i);
        finish();
    }
    public void goToSignUpPage()
    {
        Intent i = new Intent(LogInCaptinActivity.this,SignUpActivity.class);
        startActivity(i);

    }
    }

