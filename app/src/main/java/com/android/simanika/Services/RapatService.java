package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.DetailArtikelResponse;
import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.HTTP.PresensiRequest;
import com.android.simanika.Services.HTTP.RapatResponse;
import com.android.simanika.Services.HTTP.RegisterRequest;
import com.android.simanika.Services.HTTP.UpdateProfileRequest;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RapatService {

    @GET("rapat")
    Call<RapatResponse> getRapat();

    @POST("presensi")
    Call<GlobalResponse> presensi(@Body PresensiRequest presensiRequest);
}
