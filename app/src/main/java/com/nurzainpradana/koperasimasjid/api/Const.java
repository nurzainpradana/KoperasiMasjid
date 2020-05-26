package com.nurzainpradana.koperasimasjid.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    public static final String URL = "http://192.168.43.83/koperasimasjid/";
    public static Retrofit retrofit;


    public static Retrofit getApiService() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
