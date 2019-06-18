package com.example.fetare2kteam.fetare2k;

public class user_Model {

    private int img;
    private String Name;
    private String Number;

    public user_Model(int img, String name, String number) {
        this.img = img;
        Name = name;
        Number = number;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
