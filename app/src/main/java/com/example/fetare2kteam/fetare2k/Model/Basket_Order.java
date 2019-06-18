package com.example.fetare2kteam.fetare2k.Model;

import java.util.ArrayList;
import java.util.List;

public class Basket_Order {

    private String ID;
    private List<String> OrderItems;
    //private List<String> Item_ID;
    private List<Integer> OrderQuantity;

    public Basket_Order(){
        OrderItems=new ArrayList<>();
        OrderQuantity=new ArrayList<>();
    }

    public Basket_Order(String order_id, List<String> Items, List<Integer> quantitys) {
        OrderItems=new ArrayList<>();
        OrderQuantity=new ArrayList<>();
        ID = order_id;
        OrderItems.addAll(Items);
        OrderQuantity.addAll(quantitys);
    }

    public String getId() {
        return ID;
    }

    public void setId(String order_id) {
        ID = order_id;
    }

    public List<String> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<String> Items) {
        OrderItems.addAll(Items);
    }

    public List<Integer> getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(List<Integer> quantitys) {
        OrderQuantity.addAll(quantitys);
    }
}
