package com.android.simanika;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.simanika.Adapter.PanitiaAdapter;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.PresensiRequest;
import com.android.simanika.Services.HTTP.UpdateProfileRequest;
import com.android.simanika.Services.SharedPreference.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresensiActivity extends AppCompatActivity {

    private ImageView presensi_back, hasilfoto;
    private Button ambilfoto, submit;
    private Spinner spinnerPanitia;
    private Uri file;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        spinnerPanitia = findViewById(R.id.spinner_panitia);
        presensi_back = findViewById(R.id.presensiback);
        ambilfoto = findViewById(R.id.ambil_foto);
        hasilfoto = findViewById(R.id.hasil_foto);
        submit = findViewById(R.id.btnSumbit);

        presensi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create an intent to return to the previous screen
                Intent intent = new Intent(PresensiActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitPresensi();
            }
        });
    }

    public void captureImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            hasilfoto.setImageBitmap(imageBitmap);
        }
    }

    private void SubmitPresensi() {
        PresensiRequest presensiRequest = new PresensiRequest();
//        presensiRequest.setFoto(foto.getText().toString());
//        presensiRequest.setPeran(peran.getText().toString());

//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading..."); // Set message untuk dialog
//        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak
//
//        progressDialog.show(); // Menampilkan dialog

        Call<GlobalResponse> globalResponseCall = ApiClient.getRapatService(PresensiActivity.this).presensi(presensiRequest);
        globalResponseCall.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
//                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    GlobalResponse globalResponse = response.body();


                    if (!globalResponse.isError()) {
                        Toast.makeText(PresensiActivity.this, globalResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // Tampilkan dialog sukses
                        AlertDialog.Builder builder = new AlertDialog.Builder(PresensiActivity.this);
                        builder.setTitle("Daftar Hadir");
                        builder.setMessage("Daftar Hadir berhasil dikirim");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Tindakan setelah tombol OK ditekan, jika diperlukan
                                Intent intent = new Intent(PresensiActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                    }
                } else {
                    Toast.makeText(PresensiActivity.this, "Daftar Kehadiran Gagal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(PresensiActivity.this, "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}