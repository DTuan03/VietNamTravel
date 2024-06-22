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
    //Hiển thị tour combo khi khách vãng lai hoac co tai khoan, neu vang lai thu userId may tra ra la 0 con co thi userId la khac 0 và câu lệnh
    // dưới có thể hiển thị cả 2
//    public interface ComboCallBack{
//        void listCombo(List<HomeModel> listCombo);
//    }
//    public void getTour(String typeTour,ComboCallBack comboCallBack){
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                String query = "SELECT Tour.TourId, Tour.NameTour, Tour.PriceTour, ImgTour.ImgResource " +
//                        "FROM Tour " +
//                        "INNER JOIN ImgTour ON Tour.TourId = ImgTour.TourId " +
//                        "WHERE Tour.TypeTour = ? " + "AND ImgTour.ImgPosition = 1";
//                try(
//                        Connection connection = sqlServerDataSource.getConnection();
//                        PreparedStatement statement = connection.prepareStatement(query);
//                ) {
//                    statement.setString(1, typeTour);
//                    ResultSet resultSet = statement.executeQuery();
//                    // Ham tạo danh sách để chứa kết quả ben duoi
//                    List<HomeModel> tours = setListTour(resultSet);
//                    comboCallBack.listCombo(tours);
//                    Log.d("fbhfdiubhviujdvbhjudvhndfju", "juhfuidshfuijdshfuijrde  " + tours.size());
//                }catch (SQLException e){
//                    e.printStackTrace();
//                    comboCallBack.listCombo(new ArrayList<>()); // Trả về danh sách rỗng trong trường hợp lỗi
//                }
//            }
//        });
//    }
//    private List<HomeModel> setListTour(ResultSet resultSet) throws SQLException {
//        List<HomeModel> tours = new ArrayList<>();
//        while (resultSet.next()) {
//            String tourId = resultSet.getString("TourId");
//            String nameTour = resultSet.getString("NameTour");
//            int priceTour = resultSet.getInt("PriceTour");
//            String imgUrl = resultSet.getString("ImgResource");
//            HomeModel tour = new HomeModel(tourId, imgUrl, nameTour, priceTour);
//            tours.add(tour);
//        }
//        return tours;
//    }

    public interface ComboCallBack{
        void listCombo(List<HomeModel> listComboTour);
    }

    public void getComboTour(int userId, ComboCallBack comboCallBack){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT Tour.TourId, Tour.TypeTour, Tour.NameTour, Tour.PriceTour, ImgTour.ImgResource, " +
                        "CASE WHEN FavTour.UserId = ? THEN 1 ELSE 0 END AS IsFavorite " +
                        "FROM Tour " +
                        "INNER JOIN ImgTour ON Tour.TourId = ImgTour.TourId " +
                        "LEFT JOIN FavTour ON Tour.TourId = FavTour.TourId AND FavTour.UserId = ? " +
                        "WHERE ImgTour.ImgPosition = 1 AND Tour.TypeTour = 'CB'";
                try(
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query)
                ){
                    statement.setInt(1, userId);
                    statement.setInt(2, userId);
                    ResultSet resultSet = statement.executeQuery();
                    List<HomeModel> list = setDataCombo(resultSet);
                    comboCallBack.listCombo(list);
                    Log.d("So luong tour: ", "So luong tour yeu thich: " + list.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private List<HomeModel> setDataCombo(ResultSet resultSet) throws SQLException{
        List<HomeModel> combos = new ArrayList<>();
        while (resultSet.next()){
            String tourId = resultSet.getString("TourId");
            String nameTour = resultSet.getString("NameTour");
            int priceTour = resultSet.getInt("PriceTour");
            String imgUrl = resultSet.getString("ImgResource");
            int isFavorite = resultSet.getInt("IsFavorite");
            HomeModel combo = new HomeModel(tourId, imgUrl, nameTour, priceTour, isFavorite);
            combos.add(combo);
        }
        return combos;
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

    public interface DiscoverCallBack{
        void listDiscover(List<HomeModel> listDiscover);
    }
    public void getDiscover(int userId, String typeDiscover, DiscoverCallBack discoverCallBack){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query;
                if(typeDiscover.equals("recommend")){
                    query = "SELECT Tour.TourId, Tour.NameTour, Tour.PriceTour, ImgTour.ImgResource, ROUND(AVG(CAST(Feedback.Rating AS FLOAT)), 1) AS AvgRating, " +
                            "CASE WHEN FavTour.UserId = ? THEN 1 ELSE 0 END AS IsFavorite " +
                            "FROM Tour " +
                            "INNER JOIN ImgTour ON Tour.TourId = ImgTour.TourId " +
                            "INNER JOIN BookedTour ON Tour.TourId = BookedTour.TourId " +
                            "INNER JOIN Feedback ON Feedback.BookedTourId = BookedTour.BookedTourId " +
                            "LEFT JOIN FavTour ON Tour.TourId = FavTour.TourId AND FavTour.UserId = ? " +
                            "WHERE ImgTour.ImgPosition = 1 AND Tour.Recommend = 1 " +
                            "GROUP BY " +
                                " Tour.TourId, " +
                                " Tour.NameTour, " +
                                " ImgTour.ImgResource, " +
                                " Tour.PriceTour, " +
                                " FavTour.UserId;";
                }
                else {
                    query = "SELECT Tour.TourId, Tour.NameTour, Tour.PriceTour, ImgTour.ImgResource, ROUND(AVG(CAST(Feedback.Rating AS FLOAT)), 1) AS AvgRating, " +
                            "CASE WHEN FavTour.UserId = ? THEN 1 ELSE 0 END AS IsFavorite " +
                            "FROM Tour " +
                            "INNER JOIN ImgTour ON Tour.TourId = ImgTour.TourId " +
                            "INNER JOIN BookedTour ON Tour.TourId = BookedTour.TourId " +
                            "INNER JOIN Feedback ON Feedback.BookedTourId = BookedTour.BookedTourId " +
                            "LEFT JOIN FavTour ON Tour.TourId = FavTour.TourId AND FavTour.UserId = ? " +
                            "WHERE ImgTour.ImgPosition = 1 AND Tour.NotMissed = 1 " +
                            "GROUP BY " +
                            " Tour.TourId, " +
                            " Tour.NameTour, " +
                            " ImgTour.ImgResource, " +
                            " Tour.PriceTour, " +
                            " FavTour.UserId;";
                }
                try (
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query);
                ){
                    statement.setInt(1, userId);
                    statement.setInt(2, userId);
                    ResultSet resultSet = statement.executeQuery();
                    List<HomeModel> discovers = setListDiscover(resultSet);
                    discoverCallBack.listDiscover(discovers);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private List<HomeModel> setListDiscover(ResultSet resultSet) throws SQLException{
        List<HomeModel> discovers = new ArrayList<>();
        while (resultSet.next()){
            String discoverId = resultSet.getString("TourId");
            String nameTour = resultSet.getString("NameTour");
            String imgResource = resultSet.getString("ImgResource");
            int price = resultSet.getInt("PriceTour");
            float avgRating = resultSet.getFloat("AvgRating");
            int isFavorite = resultSet.getInt("IsFavorite");
//            Log.d("SAO", "SAO DANH GIA " + avgRating);
            HomeModel discover = new HomeModel(imgResource, nameTour, avgRating, price, isFavorite);
            discovers.add(discover);
        }
        return discovers;
    }

}
