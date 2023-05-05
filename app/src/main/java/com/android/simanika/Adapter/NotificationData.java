package com.android.simanika.Adapter;

public class NotificationData {
    private int id;
    private String nama;

    private String info;

    public NotificationData(int id,String nama, String info) {
        this.id = id;
        this.nama = nama;
        this.info = info;
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

    public void setNama(String judul) {
        this.nama = nama;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String divisi) {
        this.info = info;
    }

}
