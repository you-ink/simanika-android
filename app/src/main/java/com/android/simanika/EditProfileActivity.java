package com.android.simanika;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView btnback;
    private Button btnedit;
    private TextView profilName, profilBio;
    private EditText nama, nim, angkatan, email, telp;

    private Context context;

    // Konstanta untuk kode permintaan (request code)
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    // Deklarasi variabel
    private ImageView profileImage;
    private Uri imageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nama = findViewById(R.id.edit_nama);
        nim = findViewById(R.id.edit_nim);
        angkatan = findViewById(R.id.edit_angkatan);
        email = findViewById(R.id.edit_email);
        telp = findViewById(R.id.edit_notelp);
        profilName = findViewById(R.id.profile_name);
        profilBio = findViewById(R.id.profile_bio);

        btnback = findViewById(R.id.kembali);
        btnedit = findViewById(R.id.editprofile);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat dialog untuk memilih sumber foto (galeri atau kamera)
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Upload Foto Profil");
                builder.setItems(new CharSequence[]{"Ambil dari Galeri", "Ambil dengan Kamera"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Memilih foto dari galeri
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                                break;
                            case 1:
                                // Mengambil foto dengan kamera
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        getUser();
    }

    private void getUser(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog

        Call<UserResponse> userResponseCall = ApiClient.getUserService(this).userDetail();
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    nama.setText(userResponse.getNama());
                    nim.setText(userResponse.getNim());
                    angkatan.setText(userResponse.getAngkatan());
                    email.setText(userResponse.getEmail());
                    telp.setText(userResponse.getTelp());
                    profilName.setText(userResponse.getNama());
                    profilBio.setText(userResponse.getDetail_user().getDivisi().getNama()+"." + userResponse.getDetail_user().getJabatan().getNama());

                } else {
                    Toast.makeText(context, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
