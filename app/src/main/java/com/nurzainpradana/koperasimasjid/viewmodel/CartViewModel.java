package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.model.AddtoCart;
import com.nurzainpradana.koperasimasjid.model.Result;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.view.detail.DetailProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {

    public void addToCart(Context context, Integer id_product, Integer id_user, Integer quantity ){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<Result> call = request.addtocartcall( id_product,
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
                Log.e("error", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                AppUtilits.viewMessage(context, context.getString(R.string.network_error));
            }
        });
    }


}
