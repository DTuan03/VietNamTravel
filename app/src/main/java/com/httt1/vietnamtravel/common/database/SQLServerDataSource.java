package com.httt1.vietnamtravel.common.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerDataSource {

    private static final String connecString = "jdbc:jtds:sqlserver://192.168.1.9:1433;databasename=viettravel;user=sa;password=Tuan12082003@";
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Class.forName("net.sourceforge.jtds.jdbc.Driver"); // Not needed in modern JDBC drivers
            connection = DriverManager.getConnection(connecString);
            Log.d("Ket noi", "Kết nối thành công");
        } catch (SQLException e) {
            Log.e("Ket noi", "Lỗi kết nối", e);
            throw e; // Throwing SQLException for caller to handle
        }
        return connection;
    }
}
