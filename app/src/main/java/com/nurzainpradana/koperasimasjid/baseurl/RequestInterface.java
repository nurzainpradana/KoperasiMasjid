package com.nurzainpradana.koperasimasjid.api;

import com.nurzainpradana.koperasimasjid.model.response.JsonRespon;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("android/new_product.php")
    Call<JsonRespon> getNew();

    @GET("android/best_product.php")
    Call<JsonRespon> getBest();

}
