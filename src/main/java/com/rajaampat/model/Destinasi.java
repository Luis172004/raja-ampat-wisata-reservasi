package com.rajaampat.model;

public class Destinasi {
    private int idDestinasi;
    private String namaDestinasi;
    private String lokasi;
    private String deskripsi;
    private double hargaTiket;
    private int kuotaHarian;

    public Destinasi() {
    }

    public Destinasi(int idDestinasi, String namaDestinasi, String lokasi,
                      String deskripsi, double hargaTiket, int kuotaHarian) {
        this.idDestinasi = idDestinasi;
        this.namaDestinasi = namaDestinasi;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.hargaTiket = hargaTiket;
        this.kuotaHarian = kuotaHarian;
    }

    public int getIdDestinasi() {
        return idDestinasi;
    }

    public void setIdDestinasi(int idDestinasi) {
        this.idDestinasi = idDestinasi;
    }

    public String getNamaDestinasi() {
        return namaDestinasi;
    }

    public void setNamaDestinasi(String namaDestinasi) {
        this.namaDestinasi = namaDestinasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(double hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public int getKuotaHarian() {
        return kuotaHarian;
    }

    public void setKuotaHarian(int kuotaHarian) {
        this.kuotaHarian = kuotaHarian;
    }

    @Override
    public String toString() {
        return namaDestinasi;
    }
}
