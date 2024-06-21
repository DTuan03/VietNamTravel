package com.httt1.vietnamtravel.home.presenter;

import android.content.Context;

import com.httt1.vietnamtravel.home.model.HomeModel;
import com.httt1.vietnamtravel.home.model.HomeRepository;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View view;
    private final HomeRepository homeRepository;
    private List<HomeModel> list; // Biến instance để lưu trữ danh sách

    public HomePresenter(HomeContract.View view, Context context){
        this.view = view;
        this.homeRepository = new HomeRepository();
    }
    @Override
    public void getDataCombo(String typeTour, int userId) {
        if(userId == 0){ // vi ben reponsitory da thiet lap neu kh co ai thi tra ve 0
            homeRepository.getTour(typeTour, new HomeRepository.ComboCallBack() {
                @Override
                public void listCombo(List<HomeModel> listCombo) {
                    list = listCombo;
                    view.showDataCombo(list); // Cập nhật view với danh sách mới
                }
            });
        }else{
            homeRepository.checkFavoriteTour(userId, new HomeRepository.FavoriteCallBack() {
                @Override
                public void listCombo(List<HomeModel> listFavoriteTour) {
                    list = listFavoriteTour;
                    view.showDataCombo(list);
                }
            });
        }
    }

    @Override
    public void getDataVoucher() {
        homeRepository.getVoucher(new HomeRepository.VoucherCallBack() {
            @Override
            public void listVoucher(List<HomeModel> listVoucher) {
                list = listVoucher;
                view.showDataVoucher(list);
            }
        });
    }

    @Override
    public void getDataDiscover(String typeDiscover) {
        homeRepository.getDiscover(typeDiscover, new HomeRepository.DiscoverCallBack() {
            @Override
            public void listDiscover(List<HomeModel> listDiscover) {
                list = listDiscover;
                view.showDataDiscover(list);
            }
        });
    }
}
