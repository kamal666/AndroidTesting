package com.example.fetare2kteam.fetare2k;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fetare2kteam.fetare2k.Model.Captain;
import com.example.fetare2kteam.fetare2k.Model.CapteinLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.concurrent.TimeUnit;

public class verificationcodeActivity extends AppCompatActivity {
    EditText vCode;
    Button submit_btn;

    Intent intent;
    String name, email, phone, profileImg, idImg, age, gender, address, ssn;
    private String mVerificationId;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode);
        submit_btn = (Button) findViewById(R.id.submitCode);
        vCode = findViewById(R.id.vCode);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        // phone = intent.getExtras.getString("phone");
        age = intent.getStringExtra("age");
        address = intent.getStringExtra("address");
        ssn = intent.getStringExtra("ssn");
        gender = intent.getStringExtra("gender");
        profileImg = intent.getStringExtra("profileImg");
        idImg = intent.getStringExtra("idImg");
        number = "+20" + " " + phone.substring(1, 4) + " " + phone.substring(4, 7) + " " + phone.substring(7);
        sendVerificationCode(number);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = vCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    vCode.setError("Enter valid code");
                    vCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });
    }

    private void sendVerificationCode(String no) {
      //  Toast.makeText(this, no, Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                vCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(verificationcodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(verificationcodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            final String phonenumber = firebaseUser.getPhoneNumber();
                            String userid = firebaseUser.getUid();
                            Captain captain = new Captain(name, email, phone, profileImg, idImg, address, ssn, age, gender);
                            firebaseFirestore.collection("Captains").document(phonenumber).set(captain).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        GeoPoint geoPoint = new GeoPoint(0.0, 0.0);
                                        int time = (int) (System.currentTimeMillis());
                                       // Timestamp tsTemp = new Timestamp(time);
                                      //  CapteinLocation capteinLocation=new CapteinLocation(geoPoint, ServerValue.TIMESTAMP);
                                        CapteinLocation capteinLocation=new CapteinLocation(geoPoint);
                                        firebaseFirestore.collection("Captein Location").document(phonenumber).set(capteinLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                              @Override
                                                                                                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                  if (task.isSuccessful()) {
                                                                                                                                                                      Toast.makeText(verificationcodeActivity.this, "تم تسجيل بنجاح.", Toast.LENGTH_SHORT).show();
                                                                                                                                                                      Intent i = new Intent(verificationcodeActivity.this, captain_home_Activity.class);
                                                                                                                                                                      startActivity(i);
                                                                                                                                                                      finish();
                                                                                                                                                                  } else {
                                                                                                                                                                      Toast.makeText(verificationcodeActivity.this, "حدث خطأ اثناء العملية 🙁 ", Toast.LENGTH_SHORT).show();
                                                                                                                                                                  }
                                                                                                                                                              }
                                                                                                                                                          }

                                        );

                                    } else {
                                        Toast.makeText(verificationcodeActivity.this, "حدث خطأ اثناء العملية 🙁 ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                        }
                    }
                });
    }

}
