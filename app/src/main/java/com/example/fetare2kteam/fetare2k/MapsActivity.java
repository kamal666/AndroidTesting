package com.example.fetare2kteam.fetare2k;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fetare2kteam.fetare2k.Model.CapteinLocation;
import com.example.fetare2kteam.fetare2k.Model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton Captain_Location;
    private LatLng marker_Captain;
    private Marker captain_marker;
    TextView user_mobile,user_name,req1;
    Intent intent;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String number;
    User u1;

    /////////////////////////////////location Stafe///////////////////////
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
            }
        }
    }
    /////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Captain_Location = (FloatingActionButton)findViewById(R.id.floating_Location);
        Button accept_btn = (Button)findViewById(R.id.accept_button);
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this,fragment_ClientRequest.class);
                startActivity(i);
            }
        });

        /*Intent in = getIntent();
        Bundle bs = in.getExtras();
        String Name = bs.getString("userName");
        String mob = bs.getString("phones");*/
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        intent=getIntent();
        number=intent.getStringExtra("phone");

        user_name = findViewById(R.id.user_name);
        user_mobile =findViewById(R.id.user_mobile);
        req1 =findViewById(R.id.req1);
        while (number==null){
        }
      //  User u=(User)intent.getSerializableExtra("MyClass");

       firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String phonenumber = firebaseUser.getPhoneNumber();
        firebaseFirestore.collection(phonenumber).document(number).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            User u = doc.toObject(User.class);
                            user_name.setText(u.getName());
                            user_mobile.setText(u.getPhonenumber());
                            String items = "";
                            for (int i = 0; i < u.getOrderQuantity().size(); i++) {
                                if (i != u.getOrderQuantity().size() - 1) {
                                    items += u.getOrderItems().get(i) + "  " + u.getOrderQuantity().get(i) + "\n";
                                } else {
                                    items += u.getOrderItems().get(i) + "  " + u.getOrderQuantity().get(i);
                                }
                            }
                            user_name.setText(u.getName());
                            user_mobile.setText(u.getPhonenumber());
                            req1.setText(items);
                        }
                    }
                });
       // Toast.makeText(this, u.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        MapsActivity.this.finish();
        Intent intent = new Intent(MapsActivity.this, captain_home_Activity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); // optional depending on your needs
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private BitmapDescriptor ConvertFromAssetsToImage(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_place_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(60, 60, vectorDrawable.getIntrinsicWidth() + 60, vectorDrawable.getIntrinsicHeight() + 60);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private BitmapDescriptor ConvertFromAssetsToImage2(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_person_map24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(60, 60, vectorDrawable.getIntrinsicWidth() + 60, vectorDrawable.getIntrinsicHeight() + 60);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String phonenumber = firebaseUser.getPhoneNumber();

        firebaseFirestore.collection("Captein Location").document(phonenumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                         //   mMap.clear();
                            DocumentSnapshot doc = task.getResult();
                            GeoPoint geoPoint = doc.getGeoPoint("geoPoint");
                            marker_Captain = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                            captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(MapsActivity.this, R.drawable.ic_person_map24dp)));
                            CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);
                        }
                    }
                });

        firebaseFirestore.collection(firebaseUser.getPhoneNumber()).document(number).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            User u = doc.toObject(User.class);
                            LatLng Client_location = new LatLng(u.getGeoPoint().getLatitude(), u.getGeoPoint().getLongitude());

                            mMap.addMarker(new MarkerOptions().position(Client_location).title(u.getName()).icon(ConvertFromAssetsToImage(MapsActivity.this, R.drawable.ic_place_black_24dp)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(Client_location));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                        }
                    }
                });
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 15 ) );

         //marker_Captain = new LatLng(27.1943273, 31.1838647);
        Captain_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
                CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);

            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Toast.makeText(captain_home_Activity.this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
                mMap.clear();
                assert firebaseUser != null;
                final String phonenumber=firebaseUser.getPhoneNumber();
                final GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());

                CapteinLocation capteinLocation=new CapteinLocation(point);
                firebaseFirestore.collection("Captein Location").document(phonenumber).set(capteinLocation);
               /* firebaseFirestore.collection("Captein Location").document(phonenumber ).delete().addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                CapteinLocation capteinLocation=new CapteinLocation(point);
                                firebaseFirestore.collection("Captein Location").document(phonenumber).set(capteinLocation).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        }
                                );
                            }
                        }
                );*/

                /*firebaseFirestore.collection("Captein Location").document(phonenumber).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    DocumentSnapshot doc = task.getResult();
                                    GeoPoint geoPoint = doc.getGeoPoint("geoPoint");
                                    marker_Captain = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                                    captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(MapsActivity.this, R.drawable.ic_person_map24dp)));
                                   // CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                                    //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);
                                }
                            }
                        });*/
                marker_Captain = new LatLng(location.getLatitude(), location.getLongitude());
                captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(MapsActivity.this, R.drawable.ic_person_map24dp)));

                firebaseFirestore.collection(phonenumber).document(number).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    User u = doc.toObject(User.class);

                                    LatLng Client_location = new LatLng(u.getGeoPoint().getLatitude(), u.getGeoPoint().getLongitude());

                                    mMap.addMarker(new MarkerOptions().position(Client_location).title(u.getName()).icon(ConvertFromAssetsToImage(MapsActivity.this, R.drawable.ic_place_black_24dp)));
                                 //   mMap.moveCamera(CameraUpdateFactory.newLatLng(Client_location));
                                }
                            }
                        });
                Captain_Location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
                        CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);

                    }
                });

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
        }
        /////////////////////////////////////

    }
}
