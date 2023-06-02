package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.DetailArtikelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("artikel")
    Call<ArtikelResponse> getArtikel();

    @GET("artikel")
    Call<ArtikelResponse> getArtikel(@Query("search[value]") String search);

    @GET("artikel_baru")
    Call<ArtikelResponse> getNewArtikel();

    @GET("artikel/{id}")
    Call<DetailArtikelResponse> getDetailArtikel(@Path("id") String id);
}