package com.android.simanika;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelengkapanProfileActivity extends AppCompatActivity {

    private ImageView btnback;
    private EditText alamat;
    private AppCompatButton btn_bukti_mahasiswa, btn_bukti_kesanggupan, btn_website_himanika;
    private String url_bukti_mahasiswa, url_bukti_kesanggupan, nama_user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelengkapan_profile);

        btnback = findViewById(R.id.kelengkapan_back);

        alamat = findViewById(R.id.data_alamat);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KelengkapanProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        getUser();

        btn_bukti_mahasiswa = findViewById(R.id.btn_bukti_mahasiswa);
        btn_bukti_mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ApiClient.getBaseUrl()+url_bukti_mahasiswa;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);
            }
        });

        btn_bukti_kesanggupan = findViewById(R.id.btn_bukti_kesanggupan);
        btn_bukti_kesanggupan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ApiClient.getBaseUrl()+url_bukti_kesanggupan;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);
            }
        });

        btn_website_himanika = findViewById(R.id.btn_website_himanika);
        btn_website_himanika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ApiClient.getBaseUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);
            }
        });
    }

    private void getUser(){
        Call<UserResponse> userResponseCall = ApiClient.getUserService(this).userDetail();
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    if (userResponse.getAlamat() != null) {
                        alamat.setText(userResponse.getAlamat());
                    }

                    nama_user = userResponse.getNama();
                    url_bukti_mahasiswa = userResponse.getDetail_user().getBukti_mahasiswa();
                    url_bukti_kesanggupan = userResponse.getDetail_user().getBukti_kesanggupan();

                    if (url_bukti_mahasiswa == null || url_bukti_kesanggupan == null) {
                        findViewById(R.id.profile_section).setVisibility(View.GONE);
                        findViewById(R.id.profile_section_null).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.profile_section_null).setVisibility(View.GONE);
                        findViewById(R.id.profile_section).setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(KelengkapanProfileActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}