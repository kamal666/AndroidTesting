package com.example.fetare2kteam.fetare2k;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_profile extends Fragment {
   TextView txr_name,txt_phone,txt_email,txt_age,txt_gender,txt_ssn,txt_address,txt_status;
    CircleImageView userProfile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String name,phone,email,age,gender,ssn,address,profileUrl;
    Switch switchStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.profile,container,false);
        txr_name=view.findViewById(R.id.txr_name);
        txt_phone=view.findViewById(R.id.txt_phone);
        txt_email=view.findViewById(R.id.txt_email);
        txt_age=view.findViewById(R.id.txt_age);
        txt_gender=view.findViewById(R.id.txt_gender);
        txt_ssn=view.findViewById(R.id.txt_ssn);
        txt_address=view.findViewById(R.id.txt_address);
        switchStatus=view.findViewById(R.id.switchStatus);
        txt_status=view.findViewById(R.id.txt_status);

        userProfile=view.findViewById(R.id.userProfile);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String userid = firebaseUser.getPhoneNumber();
        final DocumentReference docRef = firebaseFirestore.collection("Captains").document(userid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                name="Full Name: "+doc.get("name");
                phone="Mobile number: "+doc.get("phone");
                email="Email: "+doc.get("email");
                age="Age: "+doc.get("age");
                address="Address: "+doc.get("address");
                gender="Gender: "+doc.get("grand");
                ssn="National Id: "+doc.get("ssn");
                profileUrl=(String)doc.get("imageURl");
                boolean status=doc.getBoolean("status");
                if (status){
                    switchStatus.setChecked(status);
                    txt_status.setText("Online");
                    txt_status.setTextColor(Color.parseColor("#60B663"));
                }
                else {
                    switchStatus.setChecked(status);
                    txt_status.setText("Offline");
                    txt_status.setTextColor(Color.parseColor("#2E3E4F"));
                }
                    txr_name.setText(name);
                    txt_phone.setText(phone);
                    txt_email.setText(email);
                    txt_age.setText(age);
                    txt_address.setText(address);
                    txt_gender.setText(gender);
                    txt_ssn.setText(ssn);
                    //Glide.with(getActivity()).load(profileUrl).into(userProfile);
                    if (profileUrl!=null){
                        Glide.with(getActivity()).load(profileUrl).into(userProfile);
                    }
                }
            }
        });
        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txt_status.setText("Online");
                    txt_status.setTextColor(Color.parseColor("#60B663"));
                }else{
                    txt_status.setText("Offline");
                    txt_status.setTextColor(Color.parseColor("#2E3E4F"));
                }
                DocumentReference contac=firebaseFirestore.collection("Captains").document(userid);
                contac.update("status",isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.profile);
    }
}
