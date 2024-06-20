package com.httt1.vietnamtravel.home.presenter;

import com.httt1.vietnamtravel.home.model.HomeModel;

import java.util.List;

public interface HomeContract {
    interface View{
        void showDataCombo(List<HomeModel> list);
        void showDataVoucher(List<HomeModel> list);
    }
    interface Presenter{
        void getDataCombo(String typeTour);
        void getDataVoucher();
    }
}
