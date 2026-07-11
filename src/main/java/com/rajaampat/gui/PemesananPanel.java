package com.rajaampat.gui;

import com.rajaampat.dao.PaketWisataDAO;
import com.rajaampat.dao.PemesananDAO;
import com.rajaampat.dao.WisatawanDAO;
import com.rajaampat.model.PaketWisata;
import com.rajaampat.model.Pemesanan;
import com.rajaampat.model.Wisatawan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PemesananPanel extends JPanel {

    private final PemesananDAO pemesananDAO = new PemesananDAO();
    private final WisatawanDAO wisatawanDAO = new WisatawanDAO();
    private final PaketWisataDAO paketDAO = new PaketWisataDAO();

    private JTable table;
    private DefaultTableModel tableModel;

    private JComboBox<Wisatawan> comboWisatawan;
    private JComboBox<PaketWisata> comboPaket;
    private JSpinner spinnerTanggalPesan, spinnerTanggalKunjungan;
    private JTextField txtJumlahOrang, txtTotalBayar;
    private JComboBox<String> comboStatus;
    private int selectedId = -1;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public PemesananPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadComboData();
        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Pemesanan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Wisatawan:"), gbc);
        comboWisatawan = new JComboBox<>();
        gbc.gridx = 1; form.add(comboWisatawan, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        form.add(new JLabel("Paket Wisata:"), gbc);
        comboPaket = new JComboBox<>();
        comboPaket.addActionListener(e -> hitungTotal());
        gbc.gridx = 3; form.add(comboPaket, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Tanggal Pesan:"), gbc);
        spinnerTanggalPesan = buatSpinnerTanggal();
        gbc.gridx = 1; form.add(spinnerTanggalPesan, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        form.add(new JLabel("Tanggal Kunjungan:"), gbc);
        spinnerTanggalKunjungan = buatSpinnerTanggal();
        gbc.gridx = 3; form.add(spinnerTanggalKunjungan, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Jumlah Orang:"), gbc);
        txtJumlahOrang = new JTextField("1", 18);
        txtJumlahOrang.addActionListener(e -> hitungTotal());
        gbc.gridx = 1; form.add(txtJumlahOrang, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        form.add(new JLabel("Total Bayar (Rp):"), gbc);
        txtTotalBayar = new JTextField(18);
        txtTotalBayar.setEditable(false);
        gbc.gridx = 3; form.add(txtTotalBayar, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Status:"), gbc);
        comboStatus = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});
        gbc.gridx = 1; form.add(comboStatus, gbc);

        JButton btnHitung = new JButton("Hitung Total");
        btnHitung.addActionListener(e -> hitungTotal());
        gbc.gridx = 2; form.add(btnHitung, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdateStatus = new JButton("Update Status");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersihkan = new JButton("Bersihkan");

        btnTambah.addActionListener(e -> tambahData());
        btnUpdateStatus.addActionListener(e -> updateStatus());
        btnHapus.addActionListener(e -> hapusData());
        btnBersihkan.addActionListener(e -> clearForm());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnBersihkan);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        form.add(buttonPanel, gbc);

        return form;
    }

    private JSpinner buatSpinnerTanggal() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        return spinner;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Wisatawan", "Paket", "Tgl Pesan", "Tgl Kunjungan", "Jumlah Orang", "Total Bayar", "Status"};
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

    private void loadComboData() {
        comboWisatawan.removeAllItems();
        for (Wisatawan w : wisatawanDAO.getAll()) {
            comboWisatawan.addItem(w);
        }
        comboPaket.removeAllItems();
        for (PaketWisata p : paketDAO.getAll()) {
            comboPaket.addItem(p);
        }
        hitungTotal();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Pemesanan> list = pemesananDAO.getAll();
        for (Pemesanan p : list) {
            tableModel.addRow(new Object[]{
                    p.getIdPemesanan(), p.getNamaWisatawan(), p.getNamaPaket(),
                    SDF.format(p.getTanggalPesan()), SDF.format(p.getTanggalKunjungan()),
                    p.getJumlahOrang(), p.getTotalBayar(), p.getStatus()
            });
        }
    }

    private void hitungTotal() {
        PaketWisata paket = (PaketWisata) comboPaket.getSelectedItem();
        if (paket == null) {
            txtTotalBayar.setText("0");
            return;
        }
        int jumlahOrang;
        try {
            jumlahOrang = Integer.parseInt(txtJumlahOrang.getText().trim());
        } catch (NumberFormatException ex) {
            jumlahOrang = 1;
        }
        double total = paket.getHargaPaket() * jumlahOrang;
        txtTotalBayar.setText(String.valueOf(total));
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        selectedId = (int) tableModel.getValueAt(row, 0);
        String namaWisatawan = String.valueOf(tableModel.getValueAt(row, 1));
        String namaPaket = String.valueOf(tableModel.getValueAt(row, 2));

        for (int i = 0; i < comboWisatawan.getItemCount(); i++) {
            if (comboWisatawan.getItemAt(i).getNama().equals(namaWisatawan)) {
                comboWisatawan.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < comboPaket.getItemCount(); i++) {
            if (comboPaket.getItemAt(i).getNamaPaket().equals(namaPaket)) {
                comboPaket.setSelectedIndex(i);
                break;
            }
        }
        txtJumlahOrang.setText(String.valueOf(tableModel.getValueAt(row, 5)));
        txtTotalBayar.setText(String.valueOf(tableModel.getValueAt(row, 6)));
        comboStatus.setSelectedItem(String.valueOf(tableModel.getValueAt(row, 7)));
    }

    private void tambahData() {
        try {
            Wisatawan wisatawan = (Wisatawan) comboWisatawan.getSelectedItem();
            PaketWisata paket = (PaketWisata) comboPaket.getSelectedItem();
            if (wisatawan == null || paket == null) {
                JOptionPane.showMessageDialog(this, "Pastikan data wisatawan dan paket sudah tersedia.");
                return;
            }
            hitungTotal();

            Pemesanan p = new Pemesanan();
            p.setIdWisatawan(wisatawan.getIdWisatawan());
            p.setIdPaket(paket.getIdPaket());
            p.setTanggalPesan(new java.sql.Date(((Date) spinnerTanggalPesan.getValue()).getTime()));
            p.setTanggalKunjungan(new java.sql.Date(((Date) spinnerTanggalKunjungan.getValue()).getTime()));
            p.setJumlahOrang(Integer.parseInt(txtJumlahOrang.getText().trim()));
            p.setTotalBayar(Double.parseDouble(txtTotalBayar.getText().trim()));
            p.setStatus((String) comboStatus.getSelectedItem());

            if (pemesananDAO.insert(p)) {
                JOptionPane.showMessageDialog(this, "Pemesanan berhasil ditambahkan.");
                loadData();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah orang harus berupa angka.", "Input Salah", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateStatus() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        String status = (String) comboStatus.getSelectedItem();
        if (pemesananDAO.updateStatus(selectedId, status)) {
            JOptionPane.showMessageDialog(this, "Status pemesanan berhasil diperbarui.");
            loadData();
            clearForm();
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
            if (pemesananDAO.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Pemesanan berhasil dihapus.");
                loadData();
                clearForm();
            }
        }
    }

    private void clearForm() {
        selectedId = -1;
        txtJumlahOrang.setText("1");
        comboStatus.setSelectedIndex(0);
        spinnerTanggalPesan.setValue(new Date());
        spinnerTanggalKunjungan.setValue(new Date());
        hitungTotal();
        table.clearSelection();
    }
}
