package com.httt1.vietnamtravel.home.model;

public class HomeModel {
    private int userId;
    private String tourId;
    private String urlImg;
    private String nameTour;
    private int isFavorite;
    private int price;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    private float avgrStar;

    public HomeModel(int userId, String tourId) {
        this.userId = userId;
        this.tourId = tourId;
    }
    //recomend
    public HomeModel(String urlImg, String nameTour, float avgrStar, int price) {
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.avgrStar = avgrStar;
        this.price = price;
    }

    //combo not user
    public HomeModel(String TourId, String urlImg, String nameTour, int price) {
        this.tourId = TourId;
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.price = price;
    }

    //combo co user
    public HomeModel(String TourId, String urlImg, String nameTour, int price, int isFavorite) {
        this.tourId = TourId;
        this.urlImg = urlImg;
        this.nameTour = nameTour;
        this.price = price;
        this.isFavorite = isFavorite;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String gettourId() {
        return tourId;
    }

    public void settourId(String tourId) {
        this.tourId = tourId;
    }
}
