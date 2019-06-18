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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fetare2kteam.fetare2k.Model.Basket_Order;
import com.example.fetare2kteam.fetare2k.Model.Captain_order;
import com.example.fetare2kteam.fetare2k.Model.CapteinLocation;
import com.example.fetare2kteam.fetare2k.Model.Communication;
import com.example.fetare2kteam.fetare2k.Model.Communication.*;
import com.example.fetare2kteam.fetare2k.Model.Order;
import com.example.fetare2kteam.fetare2k.Model.Send_order;
import com.example.fetare2kteam.fetare2k.Model.User;
import com.example.fetare2kteam.fetare2k.Model.User_order;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class fragment_ClientRequest extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
//, GoogleMap.OnMarkerClickListener
    ListView mList;
    ArrayList<user_Model> user_modelArrayList;
    //Requests_Adapter requests_adapter;
    private FloatingActionButton Captain_Location;
    private GoogleMap mMap;
    private LatLng marker_Captain;
    private Marker captain_marker;
    private Marker client1;
    private Marker client2;
    private Marker client3;
    private Marker client4;
    private Marker client5;
    private Marker client6;
    private Marker client7;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    public static List<User> lorder;
    private List<Marker> lmarker;
    private List<LatLng> latLngs;
    // public static ArrayList<User> lorder;
    public Communication communication;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clients_requests, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_ClientRequest);
        mapFragment.getMapAsync(this);
        communication = new Communication();
        lorder = new ArrayList<>();
        lmarker = new ArrayList<>();
        latLngs = new ArrayList<>();

        Captain_Location = (FloatingActionButton) view.findViewById(R.id.my_Location);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // lorder.addAll(communication.in);
        getListItems();
       /* firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String phonenumber = firebaseUser.getPhoneNumber();
        firebaseFirestore.collection(phonenumber).get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : list) {
                            User u=doc.toObject(User.class);
                            lorder.add(u);
                            for (int i=0;i<lorder.size();i++){
                                Toast.makeText(getActivity(), lorder.get(i).getName()+" ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
        for (int i=0;i<lorder.size();i++){
            Toast.makeText(getActivity(), lorder.get(i).getName()+" ", Toast.LENGTH_SHORT).show();
        }*/

        //Toast.makeText(getActivity(), lorder.get(0).getName()+" ", Toast.LENGTH_SHORT).show();
        return view;
    }

    private void addToList(User u) {
        lorder.add(u);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.clientsrequests);
     /*   lorder = new ArrayList<>();
        getListItems();

        for (int i=0;i<lorder.size();i++){
            Toast.makeText(getActivity(), lorder.get(i).getName()+" ", Toast.LENGTH_SHORT).show();
        }*/
    }

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

       /* for (int i=0;i<lorder.size();i++){
            Toast.makeText(getActivity(), lorder.get(i).getName()+" ", Toast.LENGTH_SHORT).show();
        }*/

        //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        googleMap.setOnMarkerClickListener(this);
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String phonenumber = firebaseUser.getPhoneNumber();

        marker_Captain = new LatLng(27.1943273, 31.1838647);
        /*captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
        CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();


        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);
        //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        firebaseFirestore.collection(phonenumber).get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : list) {
                            User u = doc.toObject(User.class);
                            //  Toast.makeText(getActivity(), u.getOrderItems().get(0), Toast.LENGTH_SHORT).show();
                            LatLng latLng = new LatLng(u.getGeoPoint().getLatitude(), u.getGeoPoint().getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title(u.getName()).icon(ConvertFromAssetsToImage(getActivity(), R.drawable.ic_place_black_24dp)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            //lorder.add(u);

                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }

                });


        Captain_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
+                CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);

            }
        });*/
        firebaseFirestore.collection("Captein Location").document(phonenumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        GeoPoint geoPoint = doc.getGeoPoint("geoPoint");
                        marker_Captain = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                        captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
                        CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();
                     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(marker_Captain));
                      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                        firebaseFirestore.collection(phonenumber).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot doc : list) {
                                            User u = doc.toObject(User.class);
                                            //  Toast.makeText(getActivity(), u.getOrderItems().get(0), Toast.LENGTH_SHORT).show();
                                            LatLng latLng = new LatLng(u.getGeoPoint().getLatitude(), u.getGeoPoint().getLongitude());
                                            mMap.addMarker(new MarkerOptions().position(latLng).title(u.getName()).icon(ConvertFromAssetsToImage(getActivity(), R.drawable.ic_place_black_24dp)));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                        }
                                    }
                                });
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

        /*firebaseFirestore.collection("Captein Location").document(firebaseUser.getPhoneNumber()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot doc = task.getResult();
                            GeoPoint geoPoint = doc.getGeoPoint("geoPoint");
                            marker_Captain = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                            captain_marker = mMap.addMarker(new MarkerOptions().position(marker_Captain).title("Captain").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map24dp)));
                            CameraPosition cp = CameraPosition.builder().target(marker_Captain).zoom(13).build();


                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);
                            //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            firebaseFirestore.collection(phonenumber).get().
                                    addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot doc : list) {
                                                User u = doc.toObject(User.class);
                                                //  Toast.makeText(getActivity(), u.getOrderItems().get(0), Toast.LENGTH_SHORT).show();
                                                LatLng latLng = new LatLng(u.getGeoPoint().getLatitude(), u.getGeoPoint().getLongitude());
                                                mMap.addMarker(new MarkerOptions().position(latLng).title(u.getName()).icon(ConvertFromAssetsToImage(getActivity(), R.drawable.ic_place_black_24dp)));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                                //lorder.add(u);

                                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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
                    }
                });*/


        //////////////////////////////Location Stafe/////////////////////

        /////////////////////////////////////


    }

    //onMarkerClick
    @Override
    public boolean onMarkerClick(Marker marker) {

       /* if (marker.equals(client1)) {
            Intent i = new Intent(getActivity(), MapsActivity.class);
            startActivity(i);
        }*/

        String id = marker.getId().toString();
        id = id.substring(1);
        final int ind =Integer.parseInt(id);
        if (ind != 0) {
            firebaseUser = mAuth.getCurrentUser();
            assert firebaseUser != null;
            final String phonenumber = firebaseUser.getPhoneNumber();
            firebaseFirestore.collection(phonenumber).get().
                    addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        /*for (int i=0;i<list.size();i++) {
                            DocumentSnapshot doc=list.get(i);
                            User u=doc.toObject(User.class);
                        }*/
                            DocumentSnapshot doc = list.get(ind - 1);
                            User u = doc.toObject(User.class);
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            //   intent.putExtra("MyClass",u.toString());
                            //intent.putExtra("name",u.getName());
                            intent.putExtra("phone", u.getPhonenumber());
                            //  intent.putExtra("items",u.getOrderItems());
                            startActivity(intent);
                        }

                    });
        }


        // Intent intent=new Intent(getActivity(),MapsActivity.class);
        //intent.putExtra("MyClass",lorder.get(i));
        /*intent.putExtra("name",lorder.get(i).getName());
        intent.putExtra("phone",lorder.get(i).getPhonenumber());*/

        //startActivity(intent);
        return false;
    }
    ///////



    public List<User> getListItems() {
        final List<User> luser = new ArrayList<>();
        firebaseUser = mAuth.getCurrentUser();

        assert firebaseUser != null;
        final String phonenumber = firebaseUser.getPhoneNumber();
        firebaseFirestore.collection("Order").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<User> luser2 = new ArrayList<>();
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : list) {
                                Captain_order captain = doc.get("captain_details", Captain_order.class);
                                String phone = captain.getPhone_Number();
                                if (phonenumber.contains(phone)) {
                                    Captain_order captain_order = doc.get("captain_details", Captain_order.class);
                                    Basket_Order basket = doc.get("basket", Basket_Order.class);
                                    User_order user = doc.get("user", User_order.class);
                                    Timestamp timestamp = doc.getTimestamp("start_time");
                                    boolean accepted = doc.getBoolean("accepted");
                                    String orderid = doc.getString("order_id");
                                    final Send_order order = new Send_order(accepted, orderid, timestamp, captain_order, basket, user);
                                    /*getUserLocation(order);
                                    for (int i=0;i<lorder.size();i++){
                                        Toast.makeText(getActivity(), lorder.get(i).getName()+" ", Toast.LENGTH_SHORT).show();
                                    }*/
                                    firebaseFirestore.collection("User Locations").document(order.getUser_order().getPhonenumber()).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        final GeoPoint geoPoint = doc.getGeoPoint("geo_point");
                                                        String name;
                                                        firebaseFirestore.collection("Users").document(order.getUser_order().getPhonenumber()).get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            DocumentSnapshot doc = task.getResult();
                                                                            String fname = doc.getString("firstName");
                                                                            String lname = doc.getString("lastName");
                                                                            User user = new User(fname + " " + lname, order.getUser_order().getPhonenumber(), order.getBasket_order().getOrderItems(), order.getBasket_order().getOrderQuantity(), geoPoint);
                                                                            firebaseFirestore.collection(phonenumber).document(user.getPhonenumber()).set(user);
                                                                            lorder.add(user);
                                                                            luser.add(user);
                                                                            communication.in.add(user);
                                                                            addToList(user);


                                                                            //      Toast.makeText(getActivity(), lorder.get(0).getName(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });


                                }
                            }
                        }
                    }
                });
        return luser;
    }

    public void getUserLocation(final Send_order order) {
        //  User userl=new User();
        firebaseFirestore.collection("User Locations").document(order.getUser_order().getPhonenumber()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            final GeoPoint geoPoint = doc.getGeoPoint("geo_point");
                            String name;
                            firebaseFirestore.collection("Users").document(order.getUser_order().getPhonenumber()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot doc = task.getResult();
                                                String fname = doc.getString("firstName");
                                                String lname = doc.getString("lastName");
                                                User user = new User(fname + " " + lname, order.getUser_order().getPhonenumber(), order.getBasket_order().getOrderItems(), order.getBasket_order().getOrderQuantity(), geoPoint);
                                                lorder.add(user);

                                                //      Toast.makeText(getActivity(), lorder.get(0).getName(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
       /* Toast.makeText(getActivity(), userl.getName(), Toast.LENGTH_SHORT).show();
        return userl;*/
    }
}
