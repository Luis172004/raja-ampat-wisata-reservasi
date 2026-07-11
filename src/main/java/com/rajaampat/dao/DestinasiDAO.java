package com.rajaampat.dao;

import com.rajaampat.db.DatabaseConnection;
import com.rajaampat.model.Destinasi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinasiDAO {

    public List<Destinasi> getAll() {
        List<Destinasi> list = new ArrayList<>();
        String sql = "SELECT * FROM destinasi ORDER BY id_destinasi";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Destinasi(
                        rs.getInt("id_destinasi"),
                        rs.getString("nama_destinasi"),
                        rs.getString("lokasi"),
                        rs.getString("deskripsi"),
                        rs.getDouble("harga_tiket"),
                        rs.getInt("kuota_harian")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Destinasi d) {
        String sql = "INSERT INTO destinasi (nama_destinasi, lokasi, deskripsi, harga_tiket, kuota_harian) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNamaDestinasi());
            ps.setString(2, d.getLokasi());
            ps.setString(3, d.getDeskripsi());
            ps.setDouble(4, d.getHargaTiket());
            ps.setInt(5, d.getKuotaHarian());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Destinasi d) {
        String sql = "UPDATE destinasi SET nama_destinasi=?, lokasi=?, deskripsi=?, harga_tiket=?, kuota_harian=? WHERE id_destinasi=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNamaDestinasi());
            ps.setString(2, d.getLokasi());
            ps.setString(3, d.getDeskripsi());
            ps.setDouble(4, d.getHargaTiket());
            ps.setInt(5, d.getKuotaHarian());
            ps.setInt(6, d.getIdDestinasi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM destinasi WHERE id_destinasi=?";
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
