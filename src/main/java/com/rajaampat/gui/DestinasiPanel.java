package com.rajaampat.gui;

import com.rajaampat.dao.DestinasiDAO;
import com.rajaampat.model.Destinasi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DestinasiPanel extends JPanel {

    private final DestinasiDAO destinasiDAO = new DestinasiDAO();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNama, txtLokasi, txtHarga, txtKuota;
    private JTextArea txtDeskripsi;
    private int selectedId = -1;

    public DestinasiPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Destinasi Wisata"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nama Destinasi:"), gbc);
        txtNama = new JTextField(20);
        gbc.gridx = 1; form.add(txtNama, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        form.add(new JLabel("Lokasi:"), gbc);
        txtLokasi = new JTextField(20);
        gbc.gridx = 3; form.add(txtLokasi, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Harga Tiket:"), gbc);
        txtHarga = new JTextField(20);
        gbc.gridx = 1; form.add(txtHarga, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        form.add(new JLabel("Kuota Harian:"), gbc);
        txtKuota = new JTextField(20);
        gbc.gridx = 3; form.add(txtKuota, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Deskripsi:"), gbc);
        txtDeskripsi = new JTextArea(3, 20);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH;
        form.add(new JScrollPane(txtDeskripsi), gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersihkan = new JButton("Bersihkan");

        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersihkan.addActionListener(e -> clearForm());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnBersihkan);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        form.add(buttonPanel, gbc);

        return form;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Nama Destinasi", "Lokasi", "Deskripsi", "Harga Tiket", "Kuota Harian"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                isiFormDariTabel();
            }
        });
        return new JScrollPane(table);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Destinasi> list = destinasiDAO.getAll();
        for (Destinasi d : list) {
            tableModel.addRow(new Object[]{
                    d.getIdDestinasi(), d.getNamaDestinasi(), d.getLokasi(),
                    d.getDeskripsi(), d.getHargaTiket(), d.getKuotaHarian()
            });
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNama.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        txtLokasi.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtDeskripsi.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        txtHarga.setText(String.valueOf(tableModel.getValueAt(row, 4)));
        txtKuota.setText(String.valueOf(tableModel.getValueAt(row, 5)));
    }

    private void tambahData() {
        try {
            Destinasi d = ambilDataForm();
            if (destinasiDAO.insert(d)) {
                JOptionPane.showMessageDialog(this, "Data destinasi berhasil ditambahkan.");
                loadData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan kuota harus berupa angka.", "Input Salah", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        try {
            Destinasi d = ambilDataForm();
            d.setIdDestinasi(selectedId);
            if (destinasiDAO.update(d)) {
                JOptionPane.showMessageDialog(this, "Data destinasi berhasil diperbarui.");
                loadData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan kuota harus berupa angka.", "Input Salah", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (destinasiDAO.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data destinasi berhasil dihapus.");
                loadData();
                clearForm();
            }
        }
    }

    private Destinasi ambilDataForm() {
        Destinasi d = new Destinasi();
        d.setNamaDestinasi(txtNama.getText().trim());
        d.setLokasi(txtLokasi.getText().trim());
        d.setDeskripsi(txtDeskripsi.getText().trim());
        d.setHargaTiket(Double.parseDouble(txtHarga.getText().trim()));
        d.setKuotaHarian(Integer.parseInt(txtKuota.getText().trim()));
        return d;
    }

    private void clearForm() {
        selectedId = -1;
        txtNama.setText("");
        txtLokasi.setText("");
        txtDeskripsi.setText("");
        txtHarga.setText("");
        txtKuota.setText("");
        table.clearSelection();
    }
}
