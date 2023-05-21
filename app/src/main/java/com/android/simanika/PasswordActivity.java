package com.android.simanika;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    private ImageView btnback;
    private EditText password, konfirm_password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        btnback = findViewById(R.id.password_back);
        password = findViewById(R.id.edit_password);
        konfirm_password = findViewById(R.id.edit_konfirm_password);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
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