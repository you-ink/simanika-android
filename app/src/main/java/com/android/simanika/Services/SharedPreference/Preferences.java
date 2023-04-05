package com.android.simanika.Services.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_EMAIL_TEREGISTER = "email",
                        KEY_PASS_TEREGISTER = "pass",
                        KEY_STATUS_LOGIN = "is_logged_in",
                        KEY_TOKEN_USER = "*****";

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

    public static void clearLoggedInUser(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_STATUS_LOGIN);
        editor.remove(KEY_TOKEN_USER);
        editor.apply();
    }
}
