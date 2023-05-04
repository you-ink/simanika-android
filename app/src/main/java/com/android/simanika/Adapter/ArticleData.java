package com.android.simanika.Adapter;

public class ArticleData {

    private int id;
    private String judul;
    private String divisi;
    private String penulis;
    private String waktu;
    private String sampul;

    public ArticleData(int id, String judul, String divisi, String penulis, String waktu, String sampul) {
        this.id = id;
        this.judul = judul;
        this.divisi = divisi;
        this.penulis = penulis;
        this.waktu = waktu;
        this.sampul = sampul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSampul() {
        return sampul;
    }

    public void setSampul(String sampul) {
        this.sampul = sampul;
    }
}
