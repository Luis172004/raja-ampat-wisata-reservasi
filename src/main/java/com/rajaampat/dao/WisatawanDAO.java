package com.rajaampat.dao;

import com.rajaampat.db.DatabaseConnection;
import com.rajaampat.model.Wisatawan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WisatawanDAO {

    public List<Wisatawan> getAll() {
        List<Wisatawan> list = new ArrayList<>();
        String sql = "SELECT * FROM wisatawan ORDER BY id_wisatawan";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Wisatawan(
                        rs.getInt("id_wisatawan"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        rs.getString("email"),
                        rs.getString("alamat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Wisatawan w) {
        String sql = "INSERT INTO wisatawan (nama, no_hp, email, alamat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getNama());
            ps.setString(2, w.getNoHp());
            ps.setString(3, w.getEmail());
            ps.setString(4, w.getAlamat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Wisatawan w) {
        String sql = "UPDATE wisatawan SET nama=?, no_hp=?, email=?, alamat=? WHERE id_wisatawan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getNama());
            ps.setString(2, w.getNoHp());
            ps.setString(3, w.getEmail());
            ps.setString(4, w.getAlamat());
            ps.setInt(5, w.getIdWisatawan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM wisatawan WHERE id_wisatawan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
