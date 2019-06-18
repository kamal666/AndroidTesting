package com.example.fetare2kteam.fetare2k.Model;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name,phonenumber;
    private List<String> orderItems;
    private List<Integer> orderQuantity;
    private GeoPoint geoPoint;
    public User(){
        geoPoint=new GeoPoint(0.0,0.0);
       orderItems=new ArrayList<>();
        orderQuantity=new ArrayList<>();
    }

    public User(String name, String phonenumber, List<String> Items, List<Integer> quantitys,GeoPoint Poin) {
        this.name = name;
        this.phonenumber = phonenumber;
        orderItems=new ArrayList<>();
        orderQuantity=new ArrayList<>();
        orderItems.addAll(Items);
        orderQuantity.addAll(quantitys);
        this.geoPoint = new GeoPoint(Poin.getLatitude(),Poin.getLongitude());
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public List<String> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<String> Items) {
        orderItems.addAll(Items);
    }

    public List<Integer> getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(List<Integer> quantitys) {
        orderQuantity.addAll(quantitys);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
