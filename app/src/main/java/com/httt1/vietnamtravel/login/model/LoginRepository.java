package com.httt1.vietnamtravel.login.model;

import android.util.Log;

import com.httt1.vietnamtravel.common.database.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginRepository {
    private final SQLServerDataSource sqlServerDataSource;
    private final ExecutorService executorService;

    public LoginRepository(){
        this.sqlServerDataSource = new SQLServerDataSource();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public interface LoginCallBack{
        void checkUser(boolean success);
    }
    public void login(LoginModel user, LoginCallBack callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT UserPhone, UserPass FROM Users WHERE UserPhone = ? AND UserPass = ?";
                try (
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query)
                ) {
                    statement.setString(1, user.getPhone());
                    statement.setString(2, user.getPass());
                    ResultSet resultSet = statement.executeQuery();
                    callback.checkUser(resultSet.next());
                } catch (SQLException e) {
                    e.printStackTrace();
                    callback.checkUser(false);
                }
            }
        });
    }

    public interface UserIdCallBack{
        void getUserId(String key, String value);
    }
    public void getUserId(LoginModel user, UserIdCallBack callBack){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT UserId FROM Users WHERE UserPhone = ? ";
                try(
                        Connection connection = sqlServerDataSource.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query);
                ){
                    statement.setString(1, user.getPhone());
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        String userId = resultSet.getString("UserId");
                        String key = "User" + userId;
                        callBack.getUserId(key, userId);
                    } else {
                        // Không tìm thấy người dùng
                        callBack.getUserId("", "");
                        Log.d("Khong", "Khong tim thay nguoi dung");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
