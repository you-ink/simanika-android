package com.android.simanika.Services.HTTP;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
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
        @SerializedName("token")
        private String token;

        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class User {
            private int id;
            private String nama;
            private Detail_user detail_user;

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

            public Detail_user getDetail_user() {
                return detail_user;
            }

            public void setDetail_user(Detail_user detail_user) {
                this.detail_user = detail_user;
            }

            public static class Detail_user {
                private int id;
                private String foto;
                private int user_id;
                private int jabatan_id;
                private Jabatan jabatan;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getFoto() {
                    return foto;
                }

                public void setFoto(String foto) {
                    this.foto = foto;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getJabatan_id() {
                    return jabatan_id;
                }

                public void setJabatan_id(int jabatan_id) {
                    this.jabatan_id = jabatan_id;
                }

                public Jabatan getJabatan() {
                    return jabatan;
                }

                public void setJabatan(Jabatan jabatan) {
                    this.jabatan = jabatan;
                }

                public static class Jabatan {
                    private int id;
                    private  String nama;

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
    }

}
