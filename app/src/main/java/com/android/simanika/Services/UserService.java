package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.HTTP.RegisterRequest;
import com.android.simanika.Services.HTTP.UpdatePasswordRequest;
import com.android.simanika.Services.HTTP.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("register")
    Call<GlobalResponse> userRegister(@Body RegisterRequest registerRequest);

    @POST("akun")
    Call<UserResponse> userDetail();

    @Multipart
    @POST("edit_profil")
    Call<GlobalResponse> userUpdateProfile(
            @Part("nama") RequestBody nama,
            @Part("telp") RequestBody telp,
            @Part("angkatan") RequestBody angkatan,
            @Part MultipartBody.Part foto
    );

    @Multipart
    @POST("edit_profil")
    Call<GlobalResponse> userUpdateProfile(
            @Part("nama") RequestBody nama,
            @Part("telp") RequestBody telp,
            @Part("angkatan") RequestBody angkatan
    );

    @POST("ubah_password")
    Call<GlobalResponse> userUpdatePassword(@Body UpdatePasswordRequest updatePaswordRequest);
}
