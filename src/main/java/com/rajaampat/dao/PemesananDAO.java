package com.rajaampat.dao;

import com.rajaampat.db.DatabaseConnection;
import com.rajaampat.model.Pemesanan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PemesananDAO {

    private static final String JOIN_QUERY =
            "SELECT pm.id_pemesanan, pm.id_wisatawan, w.nama AS nama_wisatawan, " +
            "pm.id_paket, pk.nama_paket, pm.tanggal_pesan, pm.tanggal_kunjungan, " +
            "pm.jumlah_orang, pm.total_bayar, pm.status " +
            "FROM pemesanan pm " +
            "JOIN wisatawan w ON pm.id_wisatawan = w.id_wisatawan " +
            "JOIN paket_wisata pk ON pm.id_paket = pk.id_paket ";

    public List<Pemesanan> getAll() {
        List<Pemesanan> list = new ArrayList<>();
        String sql = JOIN_QUERY + "ORDER BY pm.id_pemesanan";
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

    public boolean insert(Pemesanan p) {
        String sql = "INSERT INTO pemesanan (id_wisatawan, id_paket, tanggal_pesan, tanggal_kunjungan, jumlah_orang, total_bayar, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdWisatawan());
            ps.setInt(2, p.getIdPaket());
            ps.setDate(3, p.getTanggalPesan());
            ps.setDate(4, p.getTanggalKunjungan());
            ps.setInt(5, p.getJumlahOrang());
            ps.setDouble(6, p.getTotalBayar());
            ps.setString(7, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int idPemesanan, String status) {
        String sql = "UPDATE pemesanan SET status=? WHERE id_pemesanan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idPemesanan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pemesanan WHERE id_pemesanan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pemesanan mapRow(ResultSet rs) throws SQLException {
        return new Pemesanan(
                rs.getInt("id_pemesanan"),
                rs.getInt("id_wisatawan"),
                rs.getString("nama_wisatawan"),
                rs.getInt("id_paket"),
                rs.getString("nama_paket"),
                rs.getDate("tanggal_pesan"),
                rs.getDate("tanggal_kunjungan"),
                rs.getInt("jumlah_orang"),
                rs.getDouble("total_bayar"),
                rs.getString("status")
        );
    }
}
