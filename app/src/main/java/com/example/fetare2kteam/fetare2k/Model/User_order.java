package com.example.fetare2kteam.fetare2k.Model;

public class User_order {
    String phone_number;

    public User_order(){}

    public User_order(String phonenumber) {
        this.phone_number = phonenumber;
    }

    public String getPhonenumber() {
        return phone_number;
    }

    public void setPhonenumber(String phonenumber) {
        this.phone_number = phonenumber;
    }
}
