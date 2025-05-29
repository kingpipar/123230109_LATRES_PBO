package model;

import java.sql.Date;

public class Transaksi {
    private int id;
    private String namaPelanggan;
    private String namaObat;
    private double hargaSatuan;
    private int jumlahBeli;
    private Date tanggalTransaksi;

    public Transaksi(int id, String namaPelanggan, String namaObat, double hargaSatuan, int jumlahBeli, Date tanggalTransaksi) {
        this.id = id;
        this.namaPelanggan = namaPelanggan;
        this.namaObat = namaObat;
        this.hargaSatuan = hargaSatuan;
        this.jumlahBeli = jumlahBeli;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public Transaksi(String namaPelanggan, String namaObat, double hargaSatuan, int jumlahBeli, Date tanggalTransaksi) {
        this(0, namaPelanggan, namaObat, hargaSatuan, jumlahBeli, tanggalTransaksi);
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public String getNamaObat() { return namaObat; }
    public double getHargaSatuan() { return hargaSatuan; }
    public int getJumlahBeli() { return jumlahBeli; }
    public Date getTanggalTransaksi() { return tanggalTransaksi; }

    public void setId(int id) { this.id = id; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }
    public void setJumlahBeli(int jumlahBeli) { this.jumlahBeli = jumlahBeli; }
    public void setTanggalTransaksi(Date tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }

    public double hitungTotal() {
        double total = hargaSatuan * jumlahBeli;
        if (jumlahBeli > 5) {
            total -= total * 0.1;
        }
        return total;
    }
}
