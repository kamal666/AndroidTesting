package com.example.fetare2kteam.fetare2k;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.fetare2kteam.fetare2k.Model.Captain;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UploadImageActivity extends AppCompatActivity {

    Button uploadProfile, uploadIDCard, submitImages;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    Intent intent;
    static String Flag, profileImg, idImg;
    String name, email, phone,age,gender,address,ssn;
    FirebaseFirestore firebaseFirestore;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        uploadProfile = (Button) findViewById(R.id.uploadProfile);
        uploadIDCard = (Button) findViewById(R.id.uploadIDCard);
        submitImages = (Button) findViewById(R.id.Sumbit_images);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        intent = getIntent();

        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        age = intent.getStringExtra("age");
        address = intent.getStringExtra("address");
        ssn = intent.getStringExtra("ssn");
        gender = intent.getStringExtra("gender");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        uploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag = "profile";
                openImage();
            }
        });
        uploadIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag = "id";
                openImage();
            }
        });
        submitImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileImg!=null && idImg!=null) {
                    Intent i = new Intent(UploadImageActivity.this, verificationcodeActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("phone", phone);
                    i.putExtra("age", age);
                    i.putExtra("address", address);
                    i.putExtra("ssn", ssn);
                    i.putExtra("gender", gender);
                    i.putExtra("idImg", idImg);
                    i.putExtra("profileImg", profileImg);
                    startActivity(i);

                   /////////////////////////////////
                   /* intent = getIntent();
                    fname = intent.getStringExtra("fname");
                    lname = intent.getStringExtra("lname");
                    email = intent.getStringExtra("email");
                    phone = intent.getStringExtra("phone");
                    pass = intent.getStringExtra("pass");*/

                  /*  mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        assert firebaseUser != null;
                                        String userid = firebaseUser.getUid();
                                        Captain captain = new Captain(fname, lname, email, phone, profileImg, idImg);
                                        firebaseFirestore.collection("Captains").document(userid).set(captain).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                    assert firebaseUser != null;
                                                    String userid = firebaseUser.getUid();
                                                   // GeoPoint geoPoint=new GeoPoint(findLocation().getLatitude(),findLocation().getLongitude());
                                                    CaptainLocation captainLocation=new CaptainLocation(findLocation());
                                                    firebaseFirestore.collection("Captains Locations").document(userid).set(captainLocation);
                                                    Toast.makeText(UploadImageActivity.this, "تم تسجيل بنجاح.", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(UploadImageActivity.this, captain_home_Activity.class);
                                                    startActivity(i);
                                                    finish();
                                                } else {
                                                    Toast.makeText(UploadImageActivity.this, "حدث خطأ اثناء العملية 🙁 ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(UploadImageActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });*/

              //    signUp(fname,lname,phone,email,pass,profileImg,idImg,address,ssn,age,gender);
                } else {
                    Toast.makeText(UploadImageActivity.this, "You must enter Profile and IdCard image", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }*/
    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = (Uri) task.getResult();
                        if (Flag.equals("profile")) {
                            profileImg = downloadUri.toString();
                        } else if (Flag.equals("id")) {
                            idImg = downloadUri.toString();
                        }
                        //  String mUri=downloadUri.toString();
                      /*  reference =FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("imageURL",mUri);

                        reference.updateChildren(map);*/

                        pd.dismiss();
                    } else {
                        Toast.makeText(UploadImageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        } else {
            Toast.makeText(UploadImageActivity.this, "No image selected", Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(UploadImageActivity.this, "Upload in Progress", Toast.LENGTH_SHORT);
            } else {
                uploadImage();
            }
        }
    }

    public void signUp(final String name, final String phon, final String mail, String password, final String profile, final String id,final String add,final String nid,final String Age,final String Gender) {
      //  Toast.makeText(UploadImageActivity.this, mail+" "+password, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            Captain captain = new Captain(name, mail, phon, profile, id,add,nid,Age,Gender);
                            firebaseFirestore.collection("Captains").document(userid).set(captain).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UploadImageActivity.this, "تم تسجيل بنجاح.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(UploadImageActivity.this, captain_home_Activity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(UploadImageActivity.this, "حدث خطأ اثناء العملية 🙁 ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UploadImageActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public GeoPoint findLocation(){
        final GeoPoint[] geoPoint = new GeoPoint[1];
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.getLatitude()+"");
                double latitude=Double.parseDouble(location.getLatitude()+"");
                double longitude=Double.parseDouble(location.getLongitude()+"");
               geoPoint[0] =new GeoPoint(latitude,longitude);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        return  geoPoint[0];
    }
}
