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
                String queryTour = "SELECT TourId, NameTour, PriceTour FROM Tour WHERE TypeTour = ? ";
                String queryImgTour = "SELECT TourId, ImgResource FROM ImgTour WHERE ImgPosition = 1";
                try(
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statementTour = connection.prepareStatement(queryTour);
                        PreparedStatement statementImgTour = connection.prepareStatement(queryImgTour);
                )
                {
                    statementTour.setString(1, typeTour);
                    ResultSet resultSetTour = statementTour.executeQuery();
                    ResultSet resultSetImgTour = statementImgTour.executeQuery();
                    //Phai duyet nhu nay neu kh se chi tra về 1 kết quả, và thư viem jtdc chỉ cho phép duyet 1 lan nen kh dung duoc resultSetImgTour.beforeFirst(); // Đặt con trỏ về đầu ResultSet để duyệt lại từ đầu
                    //nen truoc khi do minh phai luu anh vao 1 danh sach roi duyet danh scah do
                    List<String> imgResources = new ArrayList<>();
                    while (resultSetImgTour.next()) {
                        String tourId = resultSetImgTour.getString("TourId");
                        String imgResource = resultSetImgTour.getString("ImgResource");
                        imgResources.add(tourId + ";" + imgResource); // Lưu theo định dạng TourId|ImgResource
                    }

                    // Tạo danh sách để chứa kết quả
                    List<HomeModel> tours = new ArrayList<>();
                    while (resultSetTour.next()) {
                        String tourId = resultSetTour.getString("TourId");
                        String nameTour = resultSetTour.getString("NameTour");
                        int priceTour = resultSetTour.getInt("PriceTour");
                        // Kiểm tra và thêm dữ liệu từ danh sách imgResources vào tours
                        for (String imgResource : imgResources) {
                            String[] parts = imgResource.split(";");
                            if (tourId.equals(parts[0])) {
                                HomeModel tour = new HomeModel(parts[1], nameTour, priceTour);
                                tours.add(tour);
                                break; // Thoát vòng lặp khi đã tìm được ảnh cho TourId này
                            }
                        }
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
