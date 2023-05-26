package com.android.simanika.Services.HTTP;

public class PresensiRequest {
    private String waktu_hadir;
    private String foto;
    private String peran;
    private String rapat_id;

    public String getWaktu_hadir() {
        return waktu_hadir;
    }

    public void setWaktu_hadir(String waktu_hadir) {
        this.waktu_hadir = waktu_hadir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPeran() {
        return peran;
    }

    public void setPeran(String peran) {
        this.peran = peran;
    }

    public String getRapat_id() {
        return rapat_id;
    }

    public void setRapat_id(String rapat_id) {
        this.rapat_id = rapat_id;
    }
}
