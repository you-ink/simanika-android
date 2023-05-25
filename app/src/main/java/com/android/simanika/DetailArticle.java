package com.android.simanika;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.simanika.Adapter.ArticleAdapter;
import com.android.simanika.Adapter.ArticleData;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.DetailArtikelResponse;
import com.android.simanika.Services.HTTP.UserResponse;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailArticle extends AppCompatActivity {

    ImageView detail_artikel_sampul;
    TextView detail_artikel_divisi;
    TextView detail_artikel_judul;
    TextView detail_artikel_penulis;
    TextView detail_artikel_tanggal;
    TextView detail_artikel_konten;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        detail_artikel_sampul = findViewById(R.id.detail_artikel_sampul);
        detail_artikel_divisi = findViewById(R.id.detail_artikel_divisi);
        detail_artikel_judul = findViewById(R.id.detail_artikel_judul);
        detail_artikel_penulis = findViewById(R.id.detail_artikel_penulis);
        detail_artikel_tanggal = findViewById(R.id.detail_artikel_tanggal);
        detail_artikel_konten = findViewById(R.id.detail_artikel_konten);
        btnBack = findViewById(R.id.detail_article_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailArticle.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getDetailArtikel();
    }
    private void getDetailArtikel(){
        ProgressDialog progressDialog = new ProgressDialog(DetailArticle.this);
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog

        Intent intent = getIntent();
        String idArtikel = intent.getStringExtra("idArtikel");

        Call<DetailArtikelResponse> detailartikelResponseCall = ApiClient.getArtikelService(DetailArticle.this).getDetailArtikel(idArtikel);
        detailartikelResponseCall.enqueue(new Callback<DetailArtikelResponse>() {
            @Override
            public void onResponse(Call<DetailArtikelResponse> call, Response<DetailArtikelResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    DetailArtikelResponse detailartikelResponse = response.body();
                    detail_artikel_divisi.setText(detailartikelResponse.getData().getDivisi().getNama());
                    detail_artikel_penulis.setText(detailartikelResponse.getData().getPenulis().getNama());
                    detail_artikel_judul.setText(detailartikelResponse.getData().getJudul());
                    detail_artikel_tanggal.setText(detailartikelResponse.getData().getTanggal());
                    detail_artikel_konten.setText(detailartikelResponse.getData().getKonten());
                    Picasso.get().load(ApiClient.getBaseUrl()+detailartikelResponse.getData().getSampul()).into(detail_artikel_sampul);

                    ImageSlider imageSlider = findViewById(R.id.artikelSlider);
                    List<SlideModel> imageList = new ArrayList<>();
                    String[] files = detailartikelResponse.getData().getFile();
                    for (String file : files) {
                        imageList.add(new SlideModel(ApiClient.getBaseUrl()+file, null, null));
                    }

                    imageSlider.setImageList(imageList, null);
                } else {
                    Toast.makeText(DetailArticle.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailArtikelResponse> call, Throwable t) {
                progressDialog.dismiss();

                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });

    }
}