CREATE DATABASE apotek;
USE apotek;

CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_pelanggan VARCHAR(100),
    nama_obat VARCHAR(100),
    harga_satuan DOUBLE,
    jumlah_beli INT,
    tanggal_transaksi DATE
);