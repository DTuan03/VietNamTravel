package com.httt1.vietnamtravel.home.model;

public class HomeModel {
    private String urlImg;
    private String nameTour;
    private int price;
    private float avgrStar;

    //recomend
    public HomeModel(String urlImg, String nameTour, float avgrStar, int price) {
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.avgrStar = avgrStar;
        this.price = price;
    }

    //combo
    public HomeModel(String urlImg, String nameTour, int price) {
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.price = price;
    }

    //Voucher
    public HomeModel(String urlImg){
        this.urlImg = urlImg;
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

    public float getavgrStar() {
        return avgrStar;
    }
    public void setavgrStar(float avgrStar) {
        this.avgrStar = avgrStar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
