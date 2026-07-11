package com.rajaampat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas untuk mengelola koneksi ke database MySQL.
 * Sesuaikan HOST, PORT, DB_NAME, USER, dan PASSWORD dengan konfigurasi lokal.
 */
public class DatabaseConnection {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "reservasi_wisata_raja_ampat";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // isi sesuai password MySQL masing-masing

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME +
            "?useSSL=false&serverTimezone=Asia/Jakarta";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL tidak ditemukan.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
