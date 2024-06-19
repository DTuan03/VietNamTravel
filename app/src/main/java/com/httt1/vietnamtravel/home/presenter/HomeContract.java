package com.httt1.vietnamtravel.home.presenter;

import com.httt1.vietnamtravel.home.model.HomeModel;

import java.util.List;

public interface HomeContract {
    interface View{
        void showData(List<HomeModel> list);
    }
    interface Presenter{
        void getData(String typeTour);
    }
}
