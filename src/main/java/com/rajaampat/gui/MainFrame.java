package com.rajaampat.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistem Reservasi Wisata Raja Ampat - Dashboard Admin");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Destinasi Wisata", new DestinasiPanel());
        tabbedPane.addTab("Paket Wisata", new PaketWisataPanel());
        tabbedPane.addTab("Data Wisatawan", new WisatawanPanel());
        tabbedPane.addTab("Pemesanan", new PemesananPanel());

        JLabel header = new JLabel("Sistem Reservasi Wisata Raja Ampat", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
}
