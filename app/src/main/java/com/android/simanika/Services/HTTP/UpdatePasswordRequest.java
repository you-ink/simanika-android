package com.android.simanika.Services.HTTP;

public class UpdatePasswordRequest {
    private String password_lama;
    private String password;
    private String password_confirmation;

    public String getPassword_lama() {
        return password_lama;
    }

    public void setPassword_lama(String password_lama) {
        this.password_lama = password_lama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}
