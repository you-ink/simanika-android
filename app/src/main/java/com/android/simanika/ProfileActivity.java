package com.android.simanika;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.AuthFragment.LoginFragment;
import com.android.simanika.Services.SharedPreference.Preferences;

public class ProfileActivity extends AppCompatActivity {

    private Button editProfileButton;
    private ImageView backbtn;
    private RelativeLayout kelengkapanButton, passButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


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
                builder.setTitle("Konfirmasi");
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
    }
}
