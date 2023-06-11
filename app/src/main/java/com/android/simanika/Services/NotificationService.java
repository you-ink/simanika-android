package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.NotifikasiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationService {

    @GET("notifikasi")
    Call<NotifikasiResponse> getNotifikasi();

    @GET("notifikasi_baru")
    Call<NotifikasiResponse> getNewNotifikasi();

}
