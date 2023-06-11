package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.DetailArtikelResponse;
import com.android.simanika.Services.HTTP.NotifikasiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {

    @GET("notifikasi")
    Call<NotifikasiResponse> getNotifikasi();

    @GET("notifikasi")
    Call<NotifikasiResponse> getNotifikasi(@Query("search[value]") String search);

    @GET("notifikasi_baru")
    Call<NotifikasiResponse> getNewNotifikasi();

}
