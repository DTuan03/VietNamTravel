package com.httt1.vietnamtravel.login.model;

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
        void onResult(boolean success);
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
                    callback.onResult(resultSet.next());
                } catch (SQLException e) {
                    e.printStackTrace();
                    callback.onResult(false);
                }
            }
        });
    }
}
