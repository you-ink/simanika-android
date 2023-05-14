package com.android.simanika.Services.HTTP;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    private int id;
    private String nama, nim, angkatan, email, telp;

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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public DetailUser getDetail_user() {
        return detail_user;
    }

    public void setDetail_user(DetailUser detail_user) {
        this.detail_user = detail_user;
    }

    @SerializedName("detail_user")
    private DetailUser detail_user;

    public static class DetailUser{
        private String foto, bukti_kesanggupan, bukti_mahasiswa, tanggal_wawancara, waktu_wawancara;

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getBukti_kesanggupan() {
            return bukti_kesanggupan;
        }

        public void setBukti_kesanggupan(String bukti_kesanggupan) {
            this.bukti_kesanggupan = bukti_kesanggupan;
        }

        public String getBukti_mahasiswa() {
            return bukti_mahasiswa;
        }

        public void setBukti_mahasiswa(String bukti_mahasiswa) {
            this.bukti_mahasiswa = bukti_mahasiswa;
        }

        public String getTanggal_wawancara() {
            return tanggal_wawancara;
        }

        public void setTanggal_wawancara(String tanggal_wawancara) {
            this.tanggal_wawancara = tanggal_wawancara;
        }

        public String getWaktu_wawancara() {
            return waktu_wawancara;
        }

        public void setWaktu_wawancara(String waktu_wawancara) {
            this.waktu_wawancara = waktu_wawancara;
        }

        public Divisi getDivisi() {
            return divisi;
        }

        public void setDivisi(Divisi divisi) {
            this.divisi = divisi;
        }

        public Jabatan getJabatan() {
            return jabatan;
        }

        public void setJabatan(Jabatan jabatan) {
            this.jabatan = jabatan;
        }

        @SerializedName("divisi")
        private Divisi divisi;

        @SerializedName("jabatan")
        private Jabatan jabatan;

        public static class Divisi{
            public String getNama() {
                return nama;
            }

            public void setNama(String nama) {
                this.nama = nama;
            }

            private String nama;
        }
        public static class Jabatan {
            private String nama;

            public String getNama() {
                return nama;
            }

            public void setNama(String nama) {
                this.nama = nama;
            }
        }
    }
}
