package com.nurzainpradana.koperasimasjid.api;

import com.nurzainpradana.koperasimasjid.model.Result;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/koperasimasjid/getMember.php")
    Call<Result> getMember();

    @FormUrlEncoded
    @POST("/koperasimasjid/createMember.php")
    Call<Result> insertMember(@Field("name") String name,
                              @Field("no_phone") String no_phone,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("address") String address,
                              @Field("date_of_birth") String date_of_birth,
                              @Field("photo_profile") String photo_profile);

    @FormUrlEncoded
    @POST("/koperasimasjid/updateMember.php")
    Call<Result> updateMember(@Field("id_member") int id_member,
                              @Field("no_phone") String no_phone,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("address") String address,
                              @Field("date_of_birth") Date date_of_birth,
                              @Field("photo_profile") String photo_profile);

    @FormUrlEncoded
    @POST("/koperasimasjid/deleteMember.php")
    Call<Result> deleteMember(@Field("id_member") int id_member);



}
