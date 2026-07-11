package com.rajaampat.model;

public class PaketWisata {
    private int idPaket;
    private String namaPaket;
    private int idDestinasi;
    private String namaDestinasi; // hasil join, untuk ditampilkan di tabel/combo
    private int durasiHari;
    private double hargaPaket;

    public PaketWisata() {
    }

    public PaketWisata(int idPaket, String namaPaket, int idDestinasi,
                        String namaDestinasi, int durasiHari, double hargaPaket) {
        this.idPaket = idPaket;
        this.namaPaket = namaPaket;
        this.idDestinasi = idDestinasi;
        this.namaDestinasi = namaDestinasi;
        this.durasiHari = durasiHari;
        this.hargaPaket = hargaPaket;
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

    public int getDurasiHari() {
        return durasiHari;
    }

    public void setDurasiHari(int durasiHari) {
        this.durasiHari = durasiHari;
    }

    public double getHargaPaket() {
        return hargaPaket;
    }

    public void setHargaPaket(double hargaPaket) {
        this.hargaPaket = hargaPaket;
    }

    @Override
    public String toString() {
        return namaPaket + " - " + namaDestinasi;
    }
}
