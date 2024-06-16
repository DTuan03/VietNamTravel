package com.httt1.vietnamtravel.regis.model;

public class RegisModel {
    private String fullName;
    private String phone;
    private String pass;

    public RegisModel(){}
    public RegisModel(String fullName, String phone, String pass) {
        this.fullName = fullName;
        this.phone = phone;
        this.pass = pass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
