package com.android.simanika.Services.HTTP;


import okhttp3.RequestBody;

public class PresensiRequest {
    private RequestBody foto;

    public RequestBody getFoto() {
        return foto;
    }

    public void setFoto(RequestBody foto) {
        this.foto = foto;
    }

    private String peran;
    private String rapat_id;

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
