package com.android.simanika.Services;

import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.HTTP.RegisterRequest;
import com.android.simanika.Services.HTTP.UpdateProfileRequest;
import com.android.simanika.Services.HTTP.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("auth/register_mobile")
    Call<GlobalResponse> userRegister(@Body RegisterRequest registerRequest);

    @POST("akun")
    Call<UserResponse> userDetail();

    @POST("edit_profil")
    Call<GlobalResponse> userUpdateProfile(@Body UpdateProfileRequest updateProfileRequest);
}
