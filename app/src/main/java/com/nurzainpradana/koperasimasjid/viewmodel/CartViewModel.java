package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.model.Cart;
import com.nurzainpradana.koperasimasjid.model.Result;
import com.nurzainpradana.koperasimasjid.model.ResultCart;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {

    private MutableLiveData<List<Cart>> listCarts = new MutableLiveData<>();
    int total = 0;

    public MutableLiveData<List<Cart>> getListCarts() {
        return listCarts;
    }

    public void addToCart(Context context, Integer id_product, Integer id_user, Integer quantity ){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<Result> call = request.addtocartcall(id_product,
                id_user, quantity);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue().toString();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    //AppUtilits.viewMessage(context, context.getString(R.string.succes_add_to_wishlist));
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    //AppUtilits.viewMessage(context, context.getString(R.string.fail_add_to_wishlist));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
                Log.e("error cart", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }

    public void getCart(Context context, Integer id_user){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<ResultCart> call = request.getCart(id_user);
        call.enqueue(new Callback<ResultCart>() {
            @Override
            public void onResponse(Call<ResultCart> call, Response<ResultCart> response) {
                if (response.body()!=null){
                    //total = Integer.parseInt(response.body().getTotal());
                    List<Cart> carts = response.body().getResult();
                    listCarts.postValue(carts);
                } else {
                    Toast.makeText(context, "Tidak Ada Produk Di Keranjang Anda" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultCart> call, Throwable t) {
                t.printStackTrace();
                Log.e("error cart", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }

    public void deleteCart(Context context, Integer id_cart, Integer id_user){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<Result> call = request.deleteCartCall(id_cart);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue().toString();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Log.d("CK 1", response.body().getMessage());
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    //AppUtilits.viewMessage(context, context.getString(R.string.succes_add_to_wishlist));
                } else {
                    Log.d("CK 2", response.body().getMessage());
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    //AppUtilits.viewMessage(context, context.getString(R.string.fail_add_to_wishlist));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
                Log.e("error cart", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }

    public void checkout(int id_transaction, int id_user, int total){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<Result> call = request.checkout(id_transaction, id_user, total);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue().toString();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Log.d("CK 1", response.body().getMessage());
                    //AppUtilits.viewMessage(context, context.getString(R.string.succes_add_to_wishlist));
                } else {
                    Log.d("CK 2", response.body().getMessage());
                    //AppUtilits.viewMessage(context, context.getString(R.string.fail_add_to_wishlist));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
                Log.e("error cart", String.valueOf(t));
            }
        });
    }

    public void checkoutItem(int id_transaction, int id_product, int harga_satuan, int quantity, int subtotal) {
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<Result> call = request.checkoutItem(id_transaction, id_product, harga_satuan, quantity, subtotal);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body()!= null){
                    String value = response.body().getValue().toString();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Log.d("CKI 1", message);
                        //AppUtilits.viewMessage(context, context.getString(R.string.succes_add_to_wishlist));
                    } else {
                        Log.d("CKI 2", message);
                        //AppUtilits.viewMessage(context, context.getString(R.string.fail_add_to_wishlist));
                    }
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
                Log.e("error CKI", String.valueOf(t));
            }
        });
    }



}

