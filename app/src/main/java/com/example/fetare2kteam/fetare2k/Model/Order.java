package com.example.fetare2kteam.fetare2k.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Order {
    boolean Accepted;
   String OrderId;
   List<String>OrderItems;
   List<Integer> OrderQuantity;
   String Captain_number;
    Timestamp timestamp;
   String Phone_number;
   public Order(){
       OrderItems=new ArrayList<>();
       OrderQuantity=new ArrayList<>();
   }

    public Order(boolean accepted, String orderId, List<String> orderI, List<Integer> orderQ, String captain_number, Timestamp timestamp, String phone_number) {
        OrderItems=new ArrayList<>();
        OrderQuantity=new ArrayList<>();
        Accepted = accepted;
        OrderId = orderId;
        OrderItems.addAll(orderI);
        OrderQuantity.addAll(orderQ);
        Captain_number = captain_number;
        this.timestamp = timestamp;
        Phone_number = phone_number;
    }

    public boolean isAccepted() {
        return Accepted;
    }

    public void setAccepted(boolean accepted) {
        Accepted = accepted;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public List<String> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<String> orderI) {
        OrderItems.addAll(orderI);
    }

    public List<Integer> getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(List<Integer> orderQ) {
        OrderQuantity.addAll(orderQ);
    }

    public String getCaptain_number() {
        return Captain_number;
    }

    public void setCaptain_number(String captain_number) {
        Captain_number = captain_number;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }
}
