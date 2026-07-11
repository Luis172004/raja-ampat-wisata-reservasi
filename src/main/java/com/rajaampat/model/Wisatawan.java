package com.rajaampat.model;

public class Wisatawan {
    private int idWisatawan;
    private String nama;
    private String noHp;
    private String email;
    private String alamat;

    public Wisatawan() {
    }

    public Wisatawan(int idWisatawan, String nama, String noHp, String email, String alamat) {
        this.idWisatawan = idWisatawan;
        this.nama = nama;
        this.noHp = noHp;
        this.email = email;
        this.alamat = alamat;
    }

    public int getIdWisatawan() {
        return idWisatawan;
    }

    public void setIdWisatawan(int idWisatawan) {
        this.idWisatawan = idWisatawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return nama;
    }
}
