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

        @SerializedName("sesID")
        private String sesID;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSesID() {
            return sesID;
        }

        public void setSesID(String sesID) {
            this.sesID = sesID;
        }
    }

}
