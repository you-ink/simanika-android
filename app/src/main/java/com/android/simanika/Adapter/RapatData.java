package com.android.simanika.Adapter;

public class RapatData {

    private int id;
    private String nama;
    private String waktu_mulai;
    private String tipe;
    private String divisi;
    private String tanggal;

    public RapatData(int id, String nama, String waktu_mulai, String tipe, String divisi, String tanggal) {
        this.id = id;
        this.nama = nama;
        this.waktu_mulai = waktu_mulai;
        this.tipe = tipe;
        this.divisi = divisi;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
