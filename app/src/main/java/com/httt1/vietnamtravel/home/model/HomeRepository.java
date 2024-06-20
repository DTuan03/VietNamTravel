package com.httt1.vietnamtravel.home.model;

import android.util.Log;

import com.httt1.vietnamtravel.common.database.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeRepository {
    private final SQLServerDataSource sqlServerDataSource;
    private final ExecutorService executorService;

    public HomeRepository() {
        this.sqlServerDataSource = new SQLServerDataSource();
        this.executorService = Executors.newSingleThreadExecutor();
    }
    public interface ComboCallBack{
        void listCombo(List<HomeModel> listCombo);
    }
    public void getTour(String typeTour,ComboCallBack comboCallBack){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT Tour.TourId, Tour.NameTour, Tour.PriceTour, ImgTour.ImgResource " +
                        "FROM Tour " +
                        "INNER JOIN ImgTour ON Tour.TourId = ImgTour.TourId " +
                        "WHERE Tour.TypeTour = ? " + "AND ImgTour.ImgPosition = 1";
                try(
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query);
                ) {
                    statement.setString(1, typeTour);
                    ResultSet resultSet = statement.executeQuery();
                    // Ham tạo danh sách để chứa kết quả ben duoi
                    List<HomeModel> tours = setListTour(resultSet);
                    comboCallBack.listCombo(tours);
                    Log.d("So luong", "So luong " + tours.size());
                }catch (SQLException e){
                    e.printStackTrace();
                    comboCallBack.listCombo(new ArrayList<>()); // Trả về danh sách rỗng trong trường hợp lỗi
                }
            }
        });
    }
    private List<HomeModel> setListTour(ResultSet resultSet) throws SQLException {
        List<HomeModel> tours = new ArrayList<>();
        while (resultSet.next()) {
            String tourId = resultSet.getString("TourId");
            String nameTour = resultSet.getString("NameTour");
            int priceTour = resultSet.getInt("PriceTour");
            String imgUrl = resultSet.getString("ImgResource");
            HomeModel tour = new HomeModel(imgUrl, nameTour, priceTour);
            tours.add(tour);
        }
        return tours;
    }

    public interface VoucherCallBack{
        void listVoucher(List<HomeModel> listVoucher);
    }
    public void getVoucher(VoucherCallBack voucherCallBack){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT Voucher.VoucherId, ImgVoucher.ImgResource " +
                                "FROM Voucher INNER JOIN ImgVoucher " +
                                "ON Voucher.VoucherId = ImgVoucher.VoucherId ";
                try (
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query);
                        ){
                    ResultSet resultSet = statement.executeQuery();
                    List<HomeModel> vouchers = setListVoucher(resultSet);
                    voucherCallBack.listVoucher(vouchers);
                    Log.d("So luong Voucher", "So luong la: " + vouchers.size());
                }catch (Exception e){
                    e.printStackTrace();
                    voucherCallBack.listVoucher(new ArrayList<>());
                }
            }
        });
    }

    private List<HomeModel> setListVoucher(ResultSet resultSet) throws SQLException {
        List<HomeModel> vouchers = new ArrayList<>();
        while (resultSet.next()) {
            String voucherId = resultSet.getString("VoucherId");
            String imgUrl = resultSet.getString("ImgResource");
            HomeModel voucher = new HomeModel(imgUrl);
            vouchers.add(voucher);
        }
        return vouchers;
    }
}
