package controller;

import model.*;
import java.sql.*;
import java.util.*;

public class TransaksiController {
    private Connection conn;

    public TransaksiController() {
        conn = Koneksi.getConnection();
    }

    public void tambahTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi (nama_pelanggan, nama_obat, harga_satuan, jumlah_beli, tanggal_transaksi) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getNamaPelanggan());
            stmt.setString(2, t.getNamaObat());
            stmt.setDouble(3, t.getHargaSatuan());
            stmt.setInt(4, t.getJumlahBeli());
            stmt.setDate(5, t.getTanggalTransaksi());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah transaksi: " + e.getMessage());
        }
    }

    public List<Transaksi> getTransaksiHariIni() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE tanggal_transaksi = CURDATE()";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaksi t = new Transaksi(
                    rs.getInt("id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_obat"),
                    rs.getDouble("harga_satuan"),
                    rs.getInt("jumlah_beli"),
                    rs.getDate("tanggal_transaksi")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }
        return list;
    }

    public void updateTransaksi(Transaksi t) {
        String sql = "UPDATE transaksi SET nama_pelanggan=?, nama_obat=?, harga_satuan=?, jumlah_beli=?, tanggal_transaksi=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getNamaPelanggan());
            stmt.setString(2, t.getNamaObat());
            stmt.setDouble(3, t.getHargaSatuan());
            stmt.setInt(4, t.getJumlahBeli());
            stmt.setDate(5, t.getTanggalTransaksi());
            stmt.setInt(6, t.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal mengupdate transaksi: " + e.getMessage());
        }
    }

    public void hapusTransaksi(int id) {
        String sql = "DELETE FROM transaksi WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus transaksi: " + e.getMessage());
        }
    }
}
