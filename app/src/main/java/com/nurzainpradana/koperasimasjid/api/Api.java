package com.nurzainpradana.koperasimasjid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nurzainpradana.koperasimasjid.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;
    public static Retrofit getApi() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
