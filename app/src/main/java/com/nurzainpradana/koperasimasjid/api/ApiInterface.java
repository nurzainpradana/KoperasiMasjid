package com.nurzainpradana.koperasimasjid.api;

import com.nurzainpradana.koperasimasjid.api.response.JsonRespon;
import com.nurzainpradana.koperasimasjid.model.AddtoCart;
import com.nurzainpradana.koperasimasjid.model.Result;
import com.nurzainpradana.koperasimasjid.model.ResultUser;
import com.nurzainpradana.koperasimasjid.model.User;

import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/user/getUser.php")
    Call<List<User>> getUser(@Field("username") String username);

    @FormUrlEncoded
    @POST("api/user/checkUsername.php")
    Call<List<User>> checkUsername(@Field("username") String username);

    @FormUrlEncoded
    @POST("api/user/uploadPhotoProfile.php")
    Call<Result> uploadPhotoProfile(@Field("image") String image,
                                        @Field("filename") String filename);

    @FormUrlEncoded
    @POST("api/user/removePhotoProfile.php")
    Call<Result> removePhotoProfile(@Field("filename") String filename);

    @GET("api/user/getAllUser.php")
    Call<ResultUser> getAllUser();

    @FormUrlEncoded
    @POST("api/user/createUser.php")
    Call<ResultUser> insertUser(@Field("name") String name,
                                @Field("no_phone") String no_phone,
                                @Field("username") String username,
                                @Field("password") String password,
                                @Field("email") String email,
                                @Field("address") String address,
                                @Field("date_of_birth") String date_of_birth,
                                @Field("photo_profile") String photo_profile);

    @FormUrlEncoded
    @POST("api/user/updateUser.php")
    Call<Result> updateUser(@Field("id_user") int id_user,
                            @Field("name") String name,
                            @Field("no_phone") String no_phone,
                            @Field("username") String username,
                            @Field("email") String email,
                            @Field("address") String address,
                            @Field("date_of_birth") Date date_of_birth,
                            @Field("photo_profile") String photo_profile);

    @FormUrlEncoded
    @POST("api/user/updatePassword.php")
    Call<Result> updatePassword(
                            @Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/deleteUser.php")
    Call<ResultUser> deleteUser(@Field("id_user") int id_user);

    //
    @GET("/koperasimasjid/api/product/new_product.php")
    Call<JsonRespon> getNew();

    @GET("/koperasimasjid/api/product/best_product.php")
    Call<JsonRespon> getBest();

    @GET("/koperasimasjid/api/product/womens_product.php")
    Call<JsonRespon> getWomens();

    @GET("/koperasimasjid/api/product/stationery_product.php")
    Call<JsonRespon> getStationery();

    //Add to Cart
    @Multipart
    @POST("/koperasimasjid/api/cart/add_to_cart.php")
    Call<AddtoCart> addtocartcall(
            @Part("securecode") RequestBody securecode,
            @Part("id_products") RequestBody id_products,
            @Part("id_user") RequestBody id_user,
            @Part("price") RequestBody price
    );

    //Add to wishlist
    @Multipart
    @POST("android/add_to_wishlist.php")
    Call<AddtoCart> addtoWishlist(
            @Part("securecode") RequestBody securecode,
            @Part("id_products") RequestBody id_products,
            @Part("id_member") RequestBody id_member,
            @Part("price") RequestBody price
    );
}
