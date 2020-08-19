package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.Result;
import com.nurzainpradana.koperasimasjid.model.ResultUser;
import com.nurzainpradana.koperasimasjid.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private MutableLiveData<List<User>> userMutableLiveData = new MutableLiveData<>();

    public void setUser(String username, Context context) {
        ApiInterface Service;
        Call<List<User>> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getUser(username);
            Call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(retrofit2.Call<List<User>> call, Response<List<User>> response) {
                    List<User> user = response.body();
                    userMutableLiveData.postValue(user);
                }

                @Override
                public void onFailure(retrofit2.Call<List<User>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("ERROR 1", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR EUY", e.getMessage());
        }
    }

    public MutableLiveData<List<User>> getUser() {
        return userMutableLiveData;
    }

    public void setCreateUser(Context context, User user){

        ApiInterface Service;
        retrofit2.Call<ResultUser> Call;
        Service = Api.getApi().create(ApiInterface.class);
        Call = Service.insertUser(user.getmName(), user.getmNoPhone(), user.getmUsername(), user.getmPassword(), user.getmEmail(), user.getmAddress(), user.getmDateOfBirth().toString(), user.getmPhotoProfile());
        Call.enqueue(new Callback<ResultUser>() {
            @Override
            public void onResponse(retrofit2.Call<ResultUser> call, Response<ResultUser> response) {
                if (response.body() != null) {
                    String value = response.body().getValue().toString();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultUser> call, Throwable t) {
                t.printStackTrace();
                Log.e("error", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpdateMember (Context context, User user){
        ApiInterface Service;
        Call<Result> Call;

        Service = Api.getApi().create(ApiInterface.class);
        Call = Service.updateUser(user.getmIdUser(), user.getmName(), user.getmNoPhone(), user.getmUsername(), user.getmEmail(), user.getmAddress(), user.getmDateOfBirth(), user.getmPhotoProfile());
        Call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                Log.d("ERROR BOSQUE", t.getMessage());
                Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

