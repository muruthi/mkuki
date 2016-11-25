package com.fernamuruthi.mkuki;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 001590 on 2016-11-24.
 */

public class App extends Application {
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.producthunt.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
