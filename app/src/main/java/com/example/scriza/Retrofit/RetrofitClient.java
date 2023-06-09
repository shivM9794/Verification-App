package com.example.scriza.Retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String BASE_URL = "https://api.verifyapi.in/api/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();


    OkHttpClient okHttpClient = UnsafeHttpClient.getUnsafeOkHttpClient();

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(80, TimeUnit.SECONDS)
            .readTimeout(80, TimeUnit.SECONDS)
            .writeTimeout(80, TimeUnit.SECONDS)
            .build();


    private RetrofitClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder.build())
                .build();


    }

    public static synchronized RetrofitClient getInstance() {

        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }


    public Api getApi() {

        return retrofit.create(Api.class);
    }
}
