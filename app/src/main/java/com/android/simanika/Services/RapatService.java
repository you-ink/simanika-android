package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.RapatResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RapatService {

    @GET("rapat_hari_ini")
    Call<RapatResponse> getRapat();

    @Multipart
    @POST("presensi")
    Call<GlobalResponse> presensi(
            @Part("peran") RequestBody peran,
            @Part("rapat_id") RequestBody rapatId,
            @Part MultipartBody.Part foto
    );

    @GET("cek_presensi/{rapat_id}")
    Call<GlobalResponse> cekPresensi(@Path("rapat_id") String rapat_id);
}
