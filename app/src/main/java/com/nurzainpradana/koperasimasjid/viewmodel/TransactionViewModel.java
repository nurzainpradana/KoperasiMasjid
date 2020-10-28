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
import com.nurzainpradana.koperasimasjid.model.Transaction;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends ViewModel {

    private MutableLiveData<List<Transaction>> listTransaction = new MutableLiveData<>();
    int total = 0;

    public MutableLiveData<List<Transaction>> getListTransaction() {
        return listTransaction;
    }

    public void setTransaction(Context context, Integer id_user){
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);

        Call<List<Transaction>> call = request.getTransaction(id_user);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                 if (response.body()!=null){
                     List<Transaction> transactions = response.body();
                     listTransaction.postValue(transactions);
                             } else {
                                 Toast.makeText(context, "Tidak Ada Transaksi", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Transaction>> call, Throwable t) {
                             t.printStackTrace();
                             Log.e("error trans", String.valueOf(t));
                             Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                             AppUtilits.viewMessage(context, context.getString(R.string.network_error));
                         }
                     });
    }
}

