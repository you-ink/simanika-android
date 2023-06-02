package com.android.simanika;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.GlobalResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresensiActivity extends AppCompatActivity {

    private ImageView presensi_back, hasilfoto;
    private File fotopresensi;
    private Button ambilfoto, submit;
    private Spinner spinnerPanitia;
    private Uri file;

    private TextView presensi_nama_rapat;
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
        presensi_nama_rapat = findViewById(R.id.presensi_nama_rapat);

        Intent intent = getIntent();
        String namaRapat = intent.getStringExtra("namaRapat");
        String idRapat = intent.getStringExtra("idRapat");
        presensi_nama_rapat.setText("Presensi "+namaRapat);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.panitia_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPanitia.setAdapter(adapter);
        
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

        checkUser(idRapat);
    }

    private void checkUser(String idRapat) {
        Call<GlobalResponse> cekPresensi = ApiClient.getRapatService(PresensiActivity.this).cekPresensi(idRapat);
        cekPresensi.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if (response.isSuccessful()){
                    GlobalResponse globalResponse = response.body();

                    if (!globalResponse.isError()) {
                        submit.setEnabled(false);
                        submit.setText("Telah Submit");
                    }
                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage());
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
            fotopresensi = bitmapToFile(imageBitmap);

            hasilfoto.setImageBitmap(imageBitmap);
        }
    }

    public File bitmapToFile(Bitmap bitmap) {
        try {
            File file = new File(getCacheDir(), "temp_image.jpg");
            file.createNewFile();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bitmapData = byteArrayOutputStream.toByteArray();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bitmapData);
            fileOutputStream.flush();
            fileOutputStream.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void SubmitPresensi() {
        Intent intent = getIntent();
        String idRapat = intent.getStringExtra("idRapat");

        try {
            File file = new File(fotopresensi.getPath());
            RequestBody fotoRequestBody = RequestBody.create(file, MediaType.parse("image/jpeg"));
            MultipartBody.Part fotoPart = MultipartBody.Part.createFormData("foto", file.getName(), fotoRequestBody);

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading..."); // Set message untuk dialog
            progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

            progressDialog.show(); // Menampilkan dialog

            Call<GlobalResponse> globalResponseCall = ApiClient.getRapatService(PresensiActivity.this)
                    .presensi(
                            RequestBody.create(spinnerPanitia.getSelectedItem().toString().toLowerCase(), MediaType.parse("text/plain")),
                            RequestBody.create(idRapat, MediaType.parse("text/plain")),
                            fotoPart
                    );
            globalResponseCall.enqueue(new Callback<GlobalResponse>() {
                @Override
                public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        GlobalResponse globalResponse = response.body();

                        if (!globalResponse.isError()) {
                            // Tampilkan dialog sukses
                            AlertDialog.Builder builder = new AlertDialog.Builder(PresensiActivity.this);
                            builder.setTitle("Daftar Hadir");
                            builder.setMessage(globalResponse.getMessage());
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Tindakan setelah tombol OK ditekan, jika diperlukan
                                    Intent intent = new Intent(PresensiActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        } else {
                            Toast.makeText(PresensiActivity.this, globalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PresensiActivity.this, "Presensi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GlobalResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PresensiActivity.this, "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(PresensiActivity.this, "Presensi Gagal.", Toast.LENGTH_SHORT).show();
        }
    }

}