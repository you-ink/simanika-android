package com.android.simanika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;
import java.util.List;

public class DetailArticle extends AppCompatActivity {

    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        btnBack = findViewById(R.id.detail_article_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailArticle.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageSlider imageSlider = findViewById(R.id.artikelSlider);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel("https://wallpaperaccess.com/full/827016.jpg", null, null));
        imageList.add(new SlideModel("https://wallpaperaccess.com/full/714374.jpg", null, null));
        imageList.add(new SlideModel("https://inisitus.com/wp-content/uploads/2021/04/modern-landscape.jpg", null, null));
        imageList.add(new SlideModel("https://wallpaperaccess.com/full/1112178.jpg", null, null));

        imageSlider.setImageList(imageList, null);
    }
}