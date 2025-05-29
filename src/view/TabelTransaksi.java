package view;

import model.Transaksi;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TabelTransaksi extends AbstractTableModel {
    private final List<Transaksi> transaksiList;
    private final String[] columnNames = {"ID", "Pelanggan", "Obat", "Harga", "Jumlah", "Tanggal"};

    public TabelTransaksi(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }

    @Override
    public int getRowCount() {
        return transaksiList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaksi t = transaksiList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> t.getId();
            case 1 -> t.getNamaPelanggan();
            case 2 -> t.getNamaObat();
            case 3 -> t.getHargaSatuan();
            case 4 -> t.getJumlahBeli();
            case 5 -> t.getTanggalTransaksi();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Transaksi getTransaksiAt(int row) {
        return transaksiList.get(row);
    }
}
