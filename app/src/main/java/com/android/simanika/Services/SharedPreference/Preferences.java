package com.android.simanika.Services.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_EMAIL_TEREGISTER = "email",
                        KEY_PASS_TEREGISTER = "pass",
                        KEY_STATUS_LOGIN = "is_logged_in",
                        KEY_TOKEN_USER = "*****",
                        KEY_USER_FOTO = "USER_FOTO",
                        KEY_USER_NAMA = "USER_NAMA",
                        KEY_USER_JABATAN = "USER_JABATAN",
                        KEY_USER_STATUS = "USER_STATUS",
                        KEY_USER_DIVISI_ID = "USER_DIVISI_ID",
                        KEY_USER_ID = "USER_ID";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredEmail(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_EMAIL_TEREGISTER, email);
        editor.apply();
    }

    public static String getRegisteredEmail(Context context) {
        return getSharedPreference(context).getString(KEY_EMAIL_TEREGISTER, "");
    }

    public static void setRegisteredPass(Context context, String pass) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TEREGISTER, pass);
        editor.apply();
    }

    public static String getRegisteredPass(Context context) {
        return getSharedPreference(context).getString(KEY_PASS_TEREGISTER, "");
    }

    public static void setLoggedInToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_TOKEN_USER, token);
        editor.apply();
    }

    public static String getLoggedInToken(Context context) {
        return getSharedPreference(context).getString(KEY_TOKEN_USER, "");
    }

    public static void setLoggedInStatus(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_LOGIN, status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context) {
        return getSharedPreference(context).getBoolean(KEY_STATUS_LOGIN, false);
    }

    public static void setLoggedInUserNama(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_NAMA, token);
        editor.apply();
    }

    public static String getLoggedInUserNama(Context context) {
        return getSharedPreference(context).getString(KEY_USER_NAMA, "");
    }

    public static void setLoggedInUserFoto(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_FOTO, token);
        editor.apply();
    }

    public static String getLoggedInUserFoto(Context context) {
        return getSharedPreference(context).getString(KEY_USER_FOTO, "");
    }

    public static void setLoggedInUserJabatan(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_JABATAN, token);
        editor.apply();
    }

    public static String getLoggedInUserJabatan(Context context) {
        return getSharedPreference(context).getString(KEY_USER_JABATAN, "");
    }

    public static void setLoggedInUserStatus(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_STATUS, token);
        editor.apply();
    }

    public static String getLoggedInUserStatus(Context context) {
        return getSharedPreference(context).getString(KEY_USER_STATUS, "");
    }

    public static void setLoggedInUserDivisiId(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_DIVISI_ID, token);
        editor.apply();
    }

    public static String getLoggedInUserDivisiId(Context context) {
        return getSharedPreference(context).getString(KEY_USER_DIVISI_ID, "");
    }

    public static void setLoggedInUserId(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_ID, token);
        editor.apply();
    }

    public static String getLoggedInUserId(Context context) {
        return getSharedPreference(context).getString(KEY_USER_ID, "");
    }

    public static void clearLoggedInUser(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_STATUS_LOGIN);
        editor.remove(KEY_TOKEN_USER);
        editor.remove(KEY_USER_FOTO);
        editor.remove(KEY_USER_NAMA);
        editor.remove(KEY_USER_JABATAN);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_STATUS);
        editor.apply();
    }
}
