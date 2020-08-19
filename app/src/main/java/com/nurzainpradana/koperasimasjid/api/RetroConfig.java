package com.nurzainpradana.koperasimasjid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nurzainpradana.koperasimasjid.BuildConfig;
import com.nurzainpradana.koperasimasjid.model.AddtoCart;
import com.nurzainpradana.koperasimasjid.util.Const;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nurzainpradana.koperasimasjid.util.Const.BASE_URL;

public class RetroConfig {

    private static RetroConfig constant;
    private ApiInterface mApiInterface;

    public static Retrofit retrofit;

    public RetroConfig(Interceptor mInterceptor) {
        mApiInterface = getApiService(mInterceptor).create(ApiInterface.class);
    }

    public static Retrofit getApiService(Interceptor interceptor) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Const.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Const.API_READ_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        okHttpClient = builder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static synchronized RetroConfig getInstance() {
        if (constant == null) {
            constant = new RetroConfig(null);
        }
        return constant;
    }


    //Add to Cart
    public Call<AddtoCart> addtoCartCall(String securcode, String id_produts, String id_member, String price) {
        return mApiInterface.addtocartcall(convertString(securcode),
                convertString(id_produts), convertString(id_member),convertString(price));
    }

    //Add to Wishlist
    public Call<AddtoCart> addtoWishlistCall(String securcode, String id_produts, String id_member, String price) {
        return mApiInterface.addtoWishlist(convertString(securcode),
                convertString(id_produts), convertString(id_member),convertString(price));
    }



    //Convert aa param into plain text
    public RequestBody convertString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), "id");
        return plainString;
    }
}