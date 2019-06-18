package com.example.fetare2kteam.fetare2k;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fetare2kteam.fetare2k.Model.CapteinLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class captain_home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

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
        setContentView(R.layout.activity_captain_home_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        db= FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        Fragment fragment = new fragment_ClientRequest();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, fragment);

        ft.commit();


        //////////////////////////////Location Stafe/////////////////////
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Toast.makeText(captain_home_Activity.this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
                assert firebaseUser != null;
                final String phonenumber=firebaseUser.getPhoneNumber();
                final GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
              //  Toast.makeText(captain_home_Activity.this, location.getLatitude()+" ", Toast.LENGTH_SHORT).show();
              /*  DocumentReference contact = db.collection("Captein Location").document("phonenumber");
                contact.update(geoPoint,geoPoint)
               // contact.update(timestamp,null)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(captain_home_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });*/
            /*    db.collection("Captein Location").document("phonenumber").update("geoPoint",point).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(captain_home_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });*/
                CapteinLocation capteinLocation=new CapteinLocation(point);
                db.collection("Captein Location").document(phonenumber).set(capteinLocation);
            /*db.collection("Captein Location").document(phonenumber).delete().addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            CapteinLocation capteinLocation=new CapteinLocation(point);
                            db.collection("Captein Location").document(phonenumber).set(capteinLocation).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
//                                           // Toast.makeText(captain_home_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                        }
                    }
            );*/
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id){
            case R.id.nav_profile:
                fragment = new fragment_profile();
                break;
            case R.id.nav_requests:
                fragment = new fragment_ClientRequest();
                break;
            case R.id.nav_contact_us:
                fragment = new fragment_Contactus();
                break;
            case R.id.nav_logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(captain_home_Activity.this,UserTypeActivity.class));
                finish();
                break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.addToBackStack(null);
            ft.commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
