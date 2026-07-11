package com.rajaampat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Form login sederhana untuk admin.
 * Kredensial default: admin / admin123
 */
public class LoginFrame extends JFrame {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Login - Sistem Reservasi Wisata Raja Ampat");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Reservasi Wisata Raja Ampat", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(this::onLogin);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        getRootPane().setDefaultButton(btnLogin);
        add(panel);
    }

    private void onLogin(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            new MainFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah.",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
