package com.android.simanika.Services.HTTP;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RapatResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Data> data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private int id;
        private String tanggal;
        private String waktu_mulai;
        private String notulensi;
        private String nama;
        private String tipe;
        private String deskripsi_tipe;

        public String getWaktu_mulai() {
            return waktu_mulai;
        }

        public void setWaktu_mulai(String waktu_mulai) {
            this.waktu_mulai = waktu_mulai;
        }

        public String getNotulensi() {
            return notulensi;
        }

        public void setNotulensi(String notulensi) {
            this.notulensi = notulensi;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTipe() {
            return tipe;
        }

        public void setTipe(String tipe) {
            this.tipe = tipe;
        }

        public String getDeskripsi_tipe() {
            return deskripsi_tipe;
        }

        public void setDeskripsi_tipe(String deskripsi_tipe) {
            this.deskripsi_tipe = deskripsi_tipe;
        }
        private Divisi divisi;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public Divisi getDivisi() {
            return divisi;
        }

        public void setDivisi(Divisi divisi) {
            this.divisi = divisi;
        }

        public static class Divisi{
            private int id;
            private String nama;

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
        }

    }
}
