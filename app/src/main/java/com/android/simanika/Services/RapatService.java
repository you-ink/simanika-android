package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.PresensiRequest;
import com.android.simanika.Services.HTTP.RapatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RapatService {

    @GET("rapat_hari_ini")
    Call<RapatResponse> getRapat();

    @POST("presensi")
    Call<GlobalResponse> presensi(@Body PresensiRequest presensiRequest);
}
