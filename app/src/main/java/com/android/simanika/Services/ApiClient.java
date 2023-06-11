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

    public static String getBaseUrl(){
        return "http://192.168.1.7:8000";
    }

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
                .baseUrl(getBaseUrl()+"/api/")
                .client(okHttpClient)
                .build();

        return retrofit;

    }

    public static UserService getUserService(Context context){
        UserService userService = getRetrofit(context).create(UserService.class);

        return userService;
    }

    public static ArticleService    getArtikelService(Context context){
        ArticleService artikelService = getRetrofit(context).create(ArticleService.class);

        return artikelService;
    }

    public static RapatService    getRapatService(Context context){
        RapatService rapatService = getRetrofit(context).create(RapatService.class);

        return rapatService;
    }

    public static NotificationService    getNotificationService(Context context){
        NotificationService notificationService = getRetrofit(context).create(NotificationService.class);

        return notificationService;
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
