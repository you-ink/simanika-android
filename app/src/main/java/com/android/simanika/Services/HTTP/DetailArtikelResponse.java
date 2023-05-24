package com.android.simanika.Services.HTTP;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class DetailArtikelResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private int id;
        private String judul;
        private String konten;
        private String sampul;
        private String tanggal;
        private Penulis penulis;
        private Divisi divisi;
        private Array file;

        public Array getFile() {
            return file;
        }

        public void setFile(Array file) {
            this.file = file;
        }

        public static class Penulis{
            private int id;
            private String nim;
            private String nama;
            private String angkatan;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNim() {
                return nim;
            }

            public void setNim(String nim) {
                this.nim = nim;
            }

            public String getNama() {
                return nama;
            }

            public void setNama(String nama) {
                this.nama = nama;
            }

            public String getAngkatan() {
                return angkatan;
            }

            public void setAngkatan(String angkatan) {
                this.angkatan = angkatan;
            }
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

        public String getKonten() {
            return konten;
        }

        public void setKonten(String konten) {
            this.konten = konten;
        }

        public String getSampul() {
            return sampul;
        }

        public void setSampul(String sampul) {
            this.sampul = sampul;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public Penulis getPenulis() {
            return penulis;
        }

        public void setPenulis(Penulis penulis) {
            this.penulis = penulis;
        }

        public Divisi getDivisi() {
            return divisi;
        }

        public void setDivisi(Divisi divisi) {
            this.divisi = divisi;
        }
    }
}
