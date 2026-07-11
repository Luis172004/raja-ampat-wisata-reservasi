package com.rajaampat.gui;

import com.rajaampat.dao.WisatawanDAO;
import com.rajaampat.model.Wisatawan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WisatawanPanel extends JPanel {

    private final WisatawanDAO wisatawanDAO = new WisatawanDAO();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNama, txtNoHp, txtEmail, txtAlamat;
    private int selectedId = -1;

    public WisatawanPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Data Wisatawan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nama:"), gbc);
        txtNama = new JTextField(18);
        gbc.gridx = 1; form.add(txtNama, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        form.add(new JLabel("No. HP:"), gbc);
        txtNoHp = new JTextField(18);
        gbc.gridx = 3; form.add(txtNoHp, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(18);
        gbc.gridx = 1; form.add(txtEmail, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        form.add(new JLabel("Alamat:"), gbc);
        txtAlamat = new JTextField(18);
        gbc.gridx = 3; form.add(txtAlamat, gbc);

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
        String[] columns = {"ID", "Nama", "No. HP", "Email", "Alamat"};
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
        List<Wisatawan> list = wisatawanDAO.getAll();
        for (Wisatawan w : list) {
            tableModel.addRow(new Object[]{
                    w.getIdWisatawan(), w.getNama(), w.getNoHp(), w.getEmail(), w.getAlamat()
            });
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNama.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        txtNoHp.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtEmail.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        txtAlamat.setText(String.valueOf(tableModel.getValueAt(row, 4)));
    }

    private void tambahData() {
        Wisatawan w = ambilDataForm();
        if (wisatawanDAO.insert(w)) {
            JOptionPane.showMessageDialog(this, "Data wisatawan berhasil ditambahkan.");
            loadData();
            clearForm();
        }
    }

    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        Wisatawan w = ambilDataForm();
        w.setIdWisatawan(selectedId);
        if (wisatawanDAO.update(w)) {
            JOptionPane.showMessageDialog(this, "Data wisatawan berhasil diperbarui.");
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
            if (wisatawanDAO.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data wisatawan berhasil dihapus.");
                loadData();
                clearForm();
            }
        }
    }

    private Wisatawan ambilDataForm() {
        Wisatawan w = new Wisatawan();
        w.setNama(txtNama.getText().trim());
        w.setNoHp(txtNoHp.getText().trim());
        w.setEmail(txtEmail.getText().trim());
        w.setAlamat(txtAlamat.getText().trim());
        return w;
    }

    private void clearForm() {
        selectedId = -1;
        txtNama.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtAlamat.setText("");
        table.clearSelection();
    }
}
