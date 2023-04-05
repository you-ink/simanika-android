package com.android.simanika.NoInternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;

public class CheckInternet extends BroadcastReceiver {
    public static void showNoInternetDialog(Context context){
        // Check internet connection
        if (!checkInternet(context)){
            // show no internet dialog
            NoInternetDialog noInternetDialog = new NoInternetDialog(context);
            noInternetDialog.setCancelable(false);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
            noInternetDialog.show();
        }
    }

    private static boolean checkInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = checkInternet(context);
        if (!status) {
            showNoInternetDialog(context);
        } else {
            NoInternetDialog activeDialog = NoInternetDialog.getActiveDialog();
            if (activeDialog != null) {
                activeDialog.dissmissDialog();
            }
        }
    }
}
