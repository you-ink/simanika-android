package com.android.simanika.NoInternet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.android.simanika.R;

public class NoInternetDialog extends Dialog {
    private static NoInternetDialog activeDialog;
    public NoInternetDialog(@NonNull Context context) {
        super(context);
    }

    public static NoInternetDialog getActiveDialog() {
        return activeDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set activeDialog ke instance dari dialog yang sedang aktif
        activeDialog = this;
    }

    @Override
    protected void onStop() {
        super.onStop();

        // set activeDialog ke null ketika dialog tidak lagi aktif
        activeDialog = null;
    }

    public void dissmissDialog() {
        // cek apakah dialog sedang aktif
        if (activeDialog != null) {
            // dialog sedang aktif, lakukan sesuatu di sini
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.no_internet_dialog_layout);

        final AppCompatButton retryBtn = findViewById(R.id.retryBtn);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity;
                Context context = getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof Activity) {
                        activity = (Activity) context;
                        // Lakukan sesuatu pada Activity
                        activity.recreate();
                        break;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
                dismiss();
            }
        });
    }


}
