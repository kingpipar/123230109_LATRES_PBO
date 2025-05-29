package model;

import java.sql.*;

public class Koneksi {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/apotek";
                String user = "root"; // Ganti sesuai user MySQL Anda
                String pass = ""; // Ganti sesuai password MySQL Anda

                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}