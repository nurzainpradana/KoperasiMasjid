package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.model.ResultMember;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMemberViewModel extends ViewModel {
    private MutableLiveData<ResultMember> listMember = new MutableLiveData<>();

    public MutableLiveData<ResultMember> getListMember() {
        return listMember;
    }

    public void setListMember() {
        ApiInterface Service;
        Call<ResultMember> Call;

        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getAllMember();
            Call.enqueue(new Callback<ResultMember>() {
                @Override
                public void onResponse(retrofit2.Call<ResultMember> call, Response<ResultMember> response) {
                    ResultMember listMembers;
                    listMembers = response.body();
                    listMember.postValue(listMembers);

                }

                @Override
                public void onFailure(retrofit2.Call<ResultMember> call, Throwable t) {
                    Log.d("Error Bosq", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
