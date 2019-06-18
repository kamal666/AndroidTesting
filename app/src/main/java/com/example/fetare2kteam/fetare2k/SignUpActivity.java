package com.example.fetare2kteam.fetare2k;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText Name, Email, Mobile,Age,SSN,Address;
    RadioButton radioMale,radioFemale;
    TextView signIn_txtView;
    Button signup_btn;
    TextView Header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signIn_txtView = (TextView) findViewById(R.id.signInNow);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Mobile = findViewById(R.id.Mobile);
        Header=findViewById(R.id.header);
        Age = findViewById(R.id.Age);
        SSN = findViewById(R.id.SSN);
        Address = findViewById(R.id.Address);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);


        signIn_txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LogInCaptinActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup_btn = (Button) findViewById(R.id.signUp_button);
        /*signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });*/
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String phone = Mobile.getText().toString().trim();
                String age = Age.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String ssn = SSN.getText().toString().trim();
                String gender="";
                if (radioMale.isChecked()){
                    gender="Male";
                }else if (radioFemale.isChecked()){
                    gender="Female";
                }else{
                    gender="";
                }
                signUp( name,phone,email,age,address,ssn,gender);
            }
        });

    }


    public void signUp(final String name, final String phone, final String mail  , final String age, final String address,final String ssn,final String gender){

        if (name.isEmpty()) {
            Name.setError("Name is required");
            Name.requestFocus();
        }
        if (phone.isEmpty()) {
            Mobile.setError("phone number is required");
            Mobile.requestFocus();
        }

        if (mail.isEmpty()) {
            Email.setError("Email is required ");
            Email.requestFocus();
        }
        if (!mail.contains("@")) {
            Email.setError("it's not Email ");
            Email.requestFocus();
        }

        if (age.isEmpty()) {
            Age.setError("Age is required");
            Age.requestFocus();
        }
        if (address.isEmpty()) {
            Address.setError("Address is required");
            Address.requestFocus();
        }
        if (ssn.isEmpty()) {
            SSN.setError("National Id is required");
            SSN.requestFocus();
        }
        if (ssn.length()!=14) {
            SSN.setError("National should be 14 numbers");
            SSN.requestFocus();
        }
        if (gender.isEmpty()) {
           // Password.setError("password is required");
            Header.setError("Gender is required");
            radioFemale.requestFocus();
            radioMale.requestFocus();
        }



        if (name.isEmpty() || phone.isEmpty() ||mail.isEmpty() ||!mail.contains("@")
                ||age.isEmpty()||address.isEmpty()||ssn.isEmpty()||ssn.length()!=14||gender.isEmpty()) {
            return;
        }
        Intent i = new Intent(SignUpActivity.this, UploadImageActivity.class);
        i.putExtra("name",name);
        i.putExtra("email",mail);
        i.putExtra("phone",phone);
        i.putExtra("age",age);
        i.putExtra("address",address);
        i.putExtra("ssn",ssn);
        i.putExtra("gender",gender);
        startActivity(i);
        /*FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent i = new Intent(SignUpActivity.this, UploadImageActivity.class);
                i.putExtra("fname",fname);
                i.putExtra("lname",lname);
                i.putExtra("email",mail);
                i.putExtra("phone",phone);
                i.putExtra("pass",pass);
                startActivity(i);
            }
        });*/


    }

    /*public void signUp() {

        Intent i = new Intent(SignUpActivity.this, UploadImageActivity.class);
        startActivity(i);
        finish();
    }*/
}

