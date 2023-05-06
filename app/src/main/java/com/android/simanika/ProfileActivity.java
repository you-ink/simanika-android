package com.android.simanika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.AuthFragment.LoginFragment;

public class ProfileActivity extends AppCompatActivity {

    private Button editProfileButton;
    private ImageView backbtn, kelengkapanButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        editProfileButton = findViewById(R.id.editprofile);
        kelengkapanButton = findViewById(R.id.btndata);
        logoutButton = findViewById(R.id.logout);
        backbtn = findViewById(R.id.profileback);


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
                Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
