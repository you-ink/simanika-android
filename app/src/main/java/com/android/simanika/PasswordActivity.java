package com.android.simanika;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.UpdatePasswordRequest;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    private ImageView btnback;
    private EditText passwordlama, password, konfirm_password;
    Button changepassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        btnback = findViewById(R.id.password_back);
        passwordlama = findViewById(R.id.password_lama);
        password = findViewById(R.id.password);
        konfirm_password = findViewById(R.id.konfirm_password);
        changepassword = findViewById(R.id.changepassword);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassword();
            }
        });
    }

    private void UpdatePassword(){
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setPassword_lama(passwordlama.getText().toString());
        updatePasswordRequest.setPassword(password.getText().toString());
        updatePasswordRequest.setPassword_confirmation(konfirm_password.getText().toString());

//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading..."); // Set message untuk dialog
//        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak
//
//        progressDialog.show(); // Menampilkan dialog

        Call<GlobalResponse> globalResponseCall = ApiClient.getUserService(PasswordActivity.this).userUpdatePassword(updatePasswordRequest);
        globalResponseCall.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
//                progressDialog.dismiss();

                if (response.isSuccessful()){
                    GlobalResponse globalResponse = response.body();
                    if (!globalResponse.isError()) {
                        Toast.makeText(PasswordActivity.this, globalResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // Tampilkan dialog sukses
                        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordActivity.this);
                        builder.setTitle("Success");
                        builder.setMessage("Password berhasil diperbarui.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Tindakan setelah tombol OK ditekan, jika diperlukan
                                Intent intent = new Intent(PasswordActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                    }
                } else {
                    Toast.makeText(PasswordActivity.this, "Update Password Gagal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {

            }
        });
    }
}