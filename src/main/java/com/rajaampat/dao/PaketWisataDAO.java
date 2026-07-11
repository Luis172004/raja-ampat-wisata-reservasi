package com.rajaampat.dao;

import com.rajaampat.db.DatabaseConnection;
import com.rajaampat.model.PaketWisata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaketWisataDAO {

    private static final String JOIN_QUERY =
            "SELECT p.id_paket, p.nama_paket, p.id_destinasi, d.nama_destinasi, p.durasi_hari, p.harga_paket " +
            "FROM paket_wisata p JOIN destinasi d ON p.id_destinasi = d.id_destinasi ";

    public List<PaketWisata> getAll() {
        List<PaketWisata> list = new ArrayList<>();
        String sql = JOIN_QUERY + "ORDER BY p.id_paket";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(PaketWisata p) {
        String sql = "INSERT INTO paket_wisata (nama_paket, id_destinasi, durasi_hari, harga_paket) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaPaket());
            ps.setInt(2, p.getIdDestinasi());
            ps.setInt(3, p.getDurasiHari());
            ps.setDouble(4, p.getHargaPaket());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PaketWisata p) {
        String sql = "UPDATE paket_wisata SET nama_paket=?, id_destinasi=?, durasi_hari=?, harga_paket=? WHERE id_paket=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaPaket());
            ps.setInt(2, p.getIdDestinasi());
            ps.setInt(3, p.getDurasiHari());
            ps.setDouble(4, p.getHargaPaket());
            ps.setInt(5, p.getIdPaket());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM paket_wisata WHERE id_paket=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private PaketWisata mapRow(ResultSet rs) throws SQLException {
        return new PaketWisata(
                rs.getInt("id_paket"),
                rs.getString("nama_paket"),
                rs.getInt("id_destinasi"),
                rs.getString("nama_destinasi"),
                rs.getInt("durasi_hari"),
                rs.getDouble("harga_paket")
        );
    }
}
