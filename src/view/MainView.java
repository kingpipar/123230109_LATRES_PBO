package view;

import controller.TransaksiController;
import model.Transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class MainView extends JFrame {
    private TransaksiController controller;
    private DefaultTableModel tableModel;
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

        tableModel = new DefaultTableModel(new String[]{"ID", "Pelanggan", "Obat", "Harga", "Jumlah", "Tanggal"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    double harga = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
                    int jumlah = Integer.parseInt(tableModel.getValueAt(row, 4).toString());
                    double total = harga * jumlah;
                    if (jumlah > 5) total *= 0.9;

                    JOptionPane.showMessageDialog(null, "Total Bayar: Rp " + total);
                }
            }
        });

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> hapusTransaksi());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnHapus, BorderLayout.SOUTH);
    }

    private void tambahTransaksi() {
        try {
            String namaPelanggan = tfNamaPelanggan.getText();
            String namaObat = tfNamaObat.getText();
            double hargaSatuan = Double.parseDouble(tfHargaSatuan.getText());
            int jumlahBeli = Integer.parseInt(tfJumlahBeli.getText());

            Transaksi t = new Transaksi(namaPelanggan, namaObat, hargaSatuan, jumlahBeli, Date.valueOf(LocalDate.now()));
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
        tableModel.setRowCount(0);
        List<Transaksi> list = controller.getTransaksiHariIni();
        for (Transaksi t : list) {
            tableModel.addRow(new Object[]{
                t.getId(), t.getNamaPelanggan(), t.getNamaObat(), t.getHargaSatuan(), t.getJumlahBeli(), t.getTanggalTransaksi()
            });
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}