package com.example.fetare2kteam.fetare2k.Model;


import com.google.firebase.Timestamp;

public class Send_order {

    private Boolean accept;
    private String order_ID ;
    private Timestamp Srart_Time ;
    private Captain_order captain_order;
    private Basket_Order basket_order;
    private User_order user_order;

    public Send_order(){
        captain_order=new Captain_order();
        basket_order=new Basket_Order();
        user_order=new User_order();
    }

    public Send_order(Boolean accept, String order_ID, Timestamp srart_Time, Captain_order captain_o, Basket_Order basket_o,User_order user_o) {
        user_order=new User_order(user_o.getPhonenumber());
        captain_order=new Captain_order(captain_o.getPhone_Number());
        basket_order=new Basket_Order(basket_o.getId(),basket_o.getOrderItems(),basket_o.getOrderQuantity());
        this.accept = accept;
        this.order_ID = order_ID;
        Srart_Time = srart_Time;
        this.captain_order = captain_order;
        this.basket_order = basket_order;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public Timestamp getSrart_Time() {
        return Srart_Time;
    }

    public void setSrart_Time(Timestamp srart_Time) {
        Srart_Time = srart_Time;
    }

    public Captain_order getCaptain_order() {
        return captain_order;
    }

    public void setCaptain_order(Captain_order captain_o) {
        captain_order.setPhone_Number(captain_o.getPhone_Number());
    }

    public Basket_Order getBasket_order() {
        return basket_order;
    }

    public void setBasket_order(Basket_Order basket_o) {
        basket_order.setId(basket_o.getId());
        basket_order.setOrderItems(basket_o.getOrderItems());
        basket_order.setOrderQuantity(basket_o.getOrderQuantity());
    }

    public User_order getUser_order() {
        return user_order;
    }

    public void setUser_order(User_order user_o) {
        user_order.setPhonenumber(user_o.getPhonenumber());
    }
}
