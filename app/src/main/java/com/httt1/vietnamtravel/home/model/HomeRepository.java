package com.httt1.vietnamtravel.home.model;

import android.util.Log;

import com.httt1.vietnamtravel.common.database.FirebaseDataSource;
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
                )
                {
                    statement.setString(1, typeTour);

                    ResultSet resultSet = statement.executeQuery();
                    // Tạo danh sách để chứa kết quả
                    List<HomeModel> tours = new ArrayList<>();
                    while (resultSet.next()) {
                        String tourId = resultSet.getString("TourId");
                        String nameTour = resultSet.getString("NameTour");
                        int priceTour = resultSet.getInt("PriceTour");
                        String imgUrl = resultSet.getString("ImgResource");
                        HomeModel tour = new HomeModel(imgUrl, nameTour, priceTour);
                        tours.add(tour);
                    }
                    comboCallBack.listCombo(tours);
                    Log.d("So luong", "So luong " + tours.size());
                }catch (SQLException e){
                    e.printStackTrace();
                    comboCallBack.listCombo(new ArrayList<>()); // Trả về danh sách rỗng trong trường hợp lỗi
                }
            }
        });
    }

}
