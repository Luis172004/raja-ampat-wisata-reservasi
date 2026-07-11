package com.rajaampat.model;

import java.sql.Date;

public class Pemesanan {
    private int idPemesanan;
    private int idWisatawan;
    private String namaWisatawan; // hasil join
    private int idPaket;
    private String namaPaket;     // hasil join
    private Date tanggalPesan;
    private Date tanggalKunjungan;
    private int jumlahOrang;
    private double totalBayar;
    private String status;

    public Pemesanan() {
    }

    public Pemesanan(int idPemesanan, int idWisatawan, String namaWisatawan,
                      int idPaket, String namaPaket, Date tanggalPesan,
                      Date tanggalKunjungan, int jumlahOrang, double totalBayar, String status) {
        this.idPemesanan = idPemesanan;
        this.idWisatawan = idWisatawan;
        this.namaWisatawan = namaWisatawan;
        this.idPaket = idPaket;
        this.namaPaket = namaPaket;
        this.tanggalPesan = tanggalPesan;
        this.tanggalKunjungan = tanggalKunjungan;
        this.jumlahOrang = jumlahOrang;
        this.totalBayar = totalBayar;
        this.status = status;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdWisatawan() {
        return idWisatawan;
    }

    public void setIdWisatawan(int idWisatawan) {
        this.idWisatawan = idWisatawan;
    }

    public String getNamaWisatawan() {
        return namaWisatawan;
    }

    public void setNamaWisatawan(String namaWisatawan) {
        this.namaWisatawan = namaWisatawan;
    }

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
    }

    public Date getTanggalPesan() {
        return tanggalPesan;
    }

    public void setTanggalPesan(Date tanggalPesan) {
        this.tanggalPesan = tanggalPesan;
    }

    public Date getTanggalKunjungan() {
        return tanggalKunjungan;
    }

    public void setTanggalKunjungan(Date tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
    }

    public int getJumlahOrang() {
        return jumlahOrang;
    }

    public void setJumlahOrang(int jumlahOrang) {
        this.jumlahOrang = jumlahOrang;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
