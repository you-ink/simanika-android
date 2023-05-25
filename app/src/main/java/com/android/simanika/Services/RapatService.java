package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.DetailArtikelResponse;
import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.HTTP.RegisterRequest;
import com.android.simanika.Services.HTTP.UpdateProfileRequest;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RapatService {

    @GET("rapat_hari_ini")
    Call<ArtikelResponse> getRapat();

    @GET("rapat/{id}")
    Call<DetailArtikelResponse> getDetailRapat(@Path("id") String id);
}
