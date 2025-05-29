package view;

import controller.TransaksiController;
import model.Transaksi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainView extends JFrame {
    private TransaksiController controller;
    private TabelTransaksi tableModel;
    private JTable table;

    private JTextField tfNamaPelanggan, tfNamaObat, tfHargaSatuan, tfJumlahBeli;

    public MainView() {
        controller = new TransaksiController();

        setTitle("Aplikasi Apotek - Penjualan Obat");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponent();
        loadData();
    }

    private void initComponent() {
        JPanel panelForm = new JPanel(new GridLayout(5, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Tambah Transaksi"));

        tfNamaPelanggan = new JTextField();
        tfNamaObat = new JTextField();
        tfHargaSatuan = new JTextField();
        tfJumlahBeli = new JTextField();

        panelForm.add(new JLabel("Nama Pelanggan:"));
        panelForm.add(tfNamaPelanggan);
        panelForm.add(new JLabel("Nama Obat:"));
        panelForm.add(tfNamaObat);
        panelForm.add(new JLabel("Harga Satuan:"));
        panelForm.add(tfHargaSatuan);
        panelForm.add(new JLabel("Jumlah Beli:"));
        panelForm.add(tfJumlahBeli);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> tambahTransaksi());
        panelForm.add(btnTambah);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());
        panelForm.add(btnRefresh);

        table = new JTable();

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> hapusTransaksi());

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editTransaksi());

        JButton btnTotalBayar = new JButton("Lihat Total Bayar");
        btnTotalBayar.addActionListener(e -> lihatTotalBayar());

        JPanel panelButton = new JPanel();
        panelButton.add(btnHapus);
        panelButton.add(btnEdit);
        panelButton.add(btnTotalBayar);

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void tambahTransaksi() {
        try {
            String namaPelanggan = tfNamaPelanggan.getText();
            String namaObat = tfNamaObat.getText();
            double hargaSatuan = Double.parseDouble(tfHargaSatuan.getText());
            int jumlahBeli = Integer.parseInt(tfJumlahBeli.getText());

            Transaksi t = new Transaksi(namaPelanggan, namaObat, hargaSatuan, jumlahBeli, java.sql.Date.valueOf(LocalDate.now()));
            controller.tambahTransaksi(t);
            loadData();

            tfNamaPelanggan.setText("");
            tfNamaObat.setText("");
            tfHargaSatuan.setText("");
            tfJumlahBeli.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        List<Transaksi> list = controller.getTransaksiHariIni();
        tableModel = new TabelTransaksi(list);
        table.setModel(tableModel);
    }

    private void hapusTransaksi() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) tableModel.getValueAt(row, 0);
                controller.hapusTransaksi(id);
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin dihapus.");
        }
    }

    private void editTransaksi() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diedit.");
            return;
        }

        try {
            Transaksi t = tableModel.getTransaksiAt(row);

            String namaPelanggan = JOptionPane.showInputDialog(this, "Edit Nama Pelanggan:", t.getNamaPelanggan());
            String namaObat = JOptionPane.showInputDialog(this, "Edit Nama Obat:", t.getNamaObat());
            String hargaStr = JOptionPane.showInputDialog(this, "Edit Harga Satuan:", t.getHargaSatuan());
            String jumlahStr = JOptionPane.showInputDialog(this, "Edit Jumlah Beli:", t.getJumlahBeli());

            if (namaPelanggan == null || namaObat == null || hargaStr == null || jumlahStr == null) {
                return; // User cancel
            }

            double hargaSatuan = Double.parseDouble(hargaStr);
            int jumlahBeli = Integer.parseInt(jumlahStr);
            java.sql.Date tanggal = java.sql.Date.valueOf(LocalDate.now());

            Transaksi updated = new Transaksi(t.getId(), namaPelanggan, namaObat, hargaSatuan, jumlahBeli, tanggal);
            controller.updateTransaksi(updated);
            loadData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lihatTotalBayar() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih salah satu transaksi terlebih dahulu.");
            return;
        }

        Transaksi t = tableModel.getTransaksiAt(row);

        double harga = t.getHargaSatuan();
        int jumlah = t.getJumlahBeli();
        double total = harga * jumlah;
        double diskon = 0;

        if (jumlah > 5) {
            diskon = total * 0.10;
            total -= diskon;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Detail Pembayaran:\n");
        sb.append("Harga satuan : Rp ").append((int) harga).append("\n");
        sb.append("Jumlah beli  : ").append(jumlah).append("\n");
        if (diskon > 0) {
            sb.append("Diskon 10%   : Rp ").append((int) diskon).append("\n");
        }
        sb.append("Total bayar  : Rp ").append((int) total);

        JOptionPane.showMessageDialog(this, sb.toString(), "Total Bayar", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
