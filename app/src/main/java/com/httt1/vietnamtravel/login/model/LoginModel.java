package com.httt1.vietnamtravel.login.model;

public class LoginModel {
    private String phone;
    private String pass;

    public LoginModel(String phone, String pass) {
        this.phone = phone;
        this.pass = pass;
    }

    public LoginModel(String phone) {
        this.phone = phone;
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
