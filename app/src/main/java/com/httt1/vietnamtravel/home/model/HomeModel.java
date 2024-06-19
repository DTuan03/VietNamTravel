package com.httt1.vietnamtravel.home.model;

public class HomeModel {
    private String urlImg;
    private String nameTour;
    private int price;
    private String numberStar;

    //recomend
    public HomeModel(String urlImg, String nameTour, String numberStar, int price) {
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.numberStar = numberStar;
        this.price = price;
    }

    //combo
    public HomeModel(String urlImg, String nameTour, int price) {
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.price = price;
    }

    public String geturlImg() {
        return urlImg;
    }

    public void seturlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getnameTour() {
        return nameTour;
    }

    public void setnameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public String getNumberStar() {
        return numberStar;
    }

    public void setNumberStar(String numberStar) {
        this.numberStar = numberStar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
