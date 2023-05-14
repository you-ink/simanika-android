package com.android.simanika;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.AuthFragment.LoginFragment;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.HTTP.UserResponse;
import com.android.simanika.Services.SharedPreference.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Button editProfileButton;
    private TextView email, nama, nim, angkatan, telp, profileName, profilBio;
    private ImageView backbtn;
    private RelativeLayout kelengkapanButton, passButton, logoutButton;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this.getBaseContext();

        email = findViewById(R.id.emailText);
        nama = findViewById(R.id.namaText);
        nim = findViewById(R.id.nimText);
        angkatan = findViewById(R.id.angkatanText);
        telp = findViewById(R.id.telpText);
        profileName = findViewById(R.id.profile_name);
        profilBio = findViewById(R.id.profie_bio);

        editProfileButton = findViewById(R.id.editprofile);
        kelengkapanButton = findViewById(R.id.btndata);
        logoutButton = findViewById(R.id.logout);
        backbtn = findViewById(R.id.profileback);
        passButton = findViewById(R.id.btnpassword);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save the changes to the profile
                // ...

                // create an intent to return to the previous screen
                Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        kelengkapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, KelengkapanProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Konfirmasi Logout");
                builder.setMessage("Apakah Anda yakin ingin logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Kode untuk logout
                        // ...

                        // Kembali ke halaman login
                        Preferences.clearLoggedInUser(view.getContext());
                        Activity thisActivity = ProfileActivity.this;
                        thisActivity.finishAfterTransition();

                        Intent intent = new Intent(thisActivity, AuthActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        getUser();
    }

    private void getUser(){
        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog


        Call<UserResponse> userResponseCall = ApiClient.getUserService(this).userDetail();
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    email.setText(userResponse.getEmail());
                    nama.setText(userResponse.getNama());
                    nim.setText(userResponse.getNim());
                    angkatan.setText(userResponse.getAngkatan());
                    telp.setText(userResponse.getTelp());
                    profileName.setText(userResponse.getNama());
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
