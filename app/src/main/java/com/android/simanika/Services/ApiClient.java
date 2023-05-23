package com.android.simanika.Services;

import android.content.Context;
import android.util.Base64;

import com.android.simanika.Services.SharedPreference.Preferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(Context context){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient;

        if (Preferences.getLoggedInToken(context).isEmpty()) {
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new HeaderInterceptor(context))
                    .build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.10.5.116:8000/api/")
                .client(okHttpClient)
                .build();

        return retrofit;

    }

    public static UserService getUserService(Context context){
        UserService userService = getRetrofit(context).create(UserService.class);

        return userService;
    }

    public static String getApiAuth() {
        return "Basic " + Base64.encodeToString("simanika_auth:$iM@n1K4_4utH".getBytes(), Base64.NO_WRAP);
    }

    public static class HeaderInterceptor implements Interceptor {
        private Context mContext;

        public HeaderInterceptor(Context context) {
            mContext = context;
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer "+Preferences.getLoggedInToken(mContext));

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }

}
