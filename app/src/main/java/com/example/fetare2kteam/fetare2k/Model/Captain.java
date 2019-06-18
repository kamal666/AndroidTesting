package com.example.fetare2kteam.fetare2k.Model;

public class Captain {
    private String name,email,phone,imageURl,idURL,address,ssn,age,grand;
    private boolean status;
    private double rate,salary;
    public Captain(){
        this.rate = 0.0;
        this.status = true;
        this.salary = 1000.0;
    }

    public Captain(String name, String email, String phone, String imageURl, String idURL, String address, String ssn, String age, String grand) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURl = imageURl;
        this.idURL = idURL;
        this.address = address;
        this.ssn = ssn;
        this.age = age;
        this.grand = grand;
        this.rate = 0.0;
        this.status = true;
        this.salary = 1000.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getIdURL() {
        return idURL;
    }

    public void setIdURL(String idURL) {
        this.idURL = idURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGrand() {
        return grand;
    }

    public void setGrand(String grand) {
        this.grand = grand;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
