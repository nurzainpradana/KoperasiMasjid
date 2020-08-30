package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.model.Result;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<Integer> result = new MutableLiveData<>();

    public MutableLiveData<Integer> getResult() {
        return result;
    }

    public void addToFavorite(Context context, Integer id_product, Integer id_user){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<Result> call = request.addtoFavorite( id_product,
                id_user);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Integer value = response.body().getValue();
                String message = response.body().getMessage();
                if (value == 1 ) {
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
                Log.e("error", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }

    public void deleteFromFavorite(Context context, Integer id_product, Integer id_user){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<Result> call = request.deleteFromFavorite( id_product, id_user);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Integer value = response.body().getValue();
                String message = response.body().getMessage();
                if (value == 1 ) {
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
                Log.e("error", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }

    public void checkFavorite (Context context, Integer id_user, Integer id_product){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<Result> call = request.checkFavorite( id_user, id_product);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                Log.d("FAVO", response.body().getMessage());

                //Toast.makeText(context, "Update Password success", Toast.LENGTH_SHORT).show();
                result.postValue(response.body().getValue());
            }

            @Override
            public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                Log.d("ERROR FAV BOSQUE", t.getMessage());
                //Toast.makeText(context, "Update password failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

