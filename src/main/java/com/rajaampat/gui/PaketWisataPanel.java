package com.rajaampat.gui;

import com.rajaampat.dao.DestinasiDAO;
import com.rajaampat.dao.PaketWisataDAO;
import com.rajaampat.model.Destinasi;
import com.rajaampat.model.PaketWisata;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PaketWisataPanel extends JPanel {

    private final PaketWisataDAO paketDAO = new PaketWisataDAO();
    private final DestinasiDAO destinasiDAO = new DestinasiDAO();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNamaPaket, txtDurasi, txtHarga;
    private JComboBox<Destinasi> comboDestinasi;
    private int selectedId = -1;

    public PaketWisataPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadDestinasiCombo();
        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Paket Wisata"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nama Paket:"), gbc);
        txtNamaPaket = new JTextField(18);
        gbc.gridx = 1; form.add(txtNamaPaket, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        form.add(new JLabel("Destinasi:"), gbc);
        comboDestinasi = new JComboBox<>();
        gbc.gridx = 3; form.add(comboDestinasi, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Durasi (hari):"), gbc);
        txtDurasi = new JTextField(18);
        gbc.gridx = 1; form.add(txtDurasi, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        form.add(new JLabel("Harga Paket:"), gbc);
        txtHarga = new JTextField(18);
        gbc.gridx = 3; form.add(txtHarga, gbc);

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

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        form.add(buttonPanel, gbc);

        return form;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Nama Paket", "Destinasi", "Durasi (hari)", "Harga Paket"};
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

    private void loadDestinasiCombo() {
        comboDestinasi.removeAllItems();
        List<Destinasi> list = destinasiDAO.getAll();
        for (Destinasi d : list) {
            comboDestinasi.addItem(d);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<PaketWisata> list = paketDAO.getAll();
        for (PaketWisata p : list) {
            tableModel.addRow(new Object[]{
                    p.getIdPaket(), p.getNamaPaket(), p.getNamaDestinasi(),
                    p.getDurasiHari(), p.getHargaPaket()
            });
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNamaPaket.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        String namaDestinasi = String.valueOf(tableModel.getValueAt(row, 2));
        for (int i = 0; i < comboDestinasi.getItemCount(); i++) {
            if (comboDestinasi.getItemAt(i).getNamaDestinasi().equals(namaDestinasi)) {
                comboDestinasi.setSelectedIndex(i);
                break;
            }
        }
        txtDurasi.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        txtHarga.setText(String.valueOf(tableModel.getValueAt(row, 4)));
    }

    private void tambahData() {
        try {
            PaketWisata p = ambilDataForm();
            if (paketDAO.insert(p)) {
                JOptionPane.showMessageDialog(this, "Paket wisata berhasil ditambahkan.");
                loadData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Durasi dan harga harus berupa angka.", "Input Salah", JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Pilih destinasi terlebih dahulu.", "Input Salah", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        try {
            PaketWisata p = ambilDataForm();
            p.setIdPaket(selectedId);
            if (paketDAO.update(p)) {
                JOptionPane.showMessageDialog(this, "Paket wisata berhasil diperbarui.");
                loadData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Durasi dan harga harus berupa angka.", "Input Salah", JOptionPane.WARNING_MESSAGE);
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
            if (paketDAO.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Paket wisata berhasil dihapus.");
                loadData();
                clearForm();
            }
        }
    }

    private PaketWisata ambilDataForm() {
        Destinasi destinasi = (Destinasi) comboDestinasi.getSelectedItem();
        PaketWisata p = new PaketWisata();
        p.setNamaPaket(txtNamaPaket.getText().trim());
        p.setIdDestinasi(destinasi.getIdDestinasi());
        p.setDurasiHari(Integer.parseInt(txtDurasi.getText().trim()));
        p.setHargaPaket(Double.parseDouble(txtHarga.getText().trim()));
        return p;
    }

    private void clearForm() {
        selectedId = -1;
        txtNamaPaket.setText("");
        txtDurasi.setText("");
        txtHarga.setText("");
        if (comboDestinasi.getItemCount() > 0) comboDestinasi.setSelectedIndex(0);
        table.clearSelection();
    }
}
