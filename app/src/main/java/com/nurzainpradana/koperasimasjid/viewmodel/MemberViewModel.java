package com.nurzainpradana.koperasimasjid.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberViewModel  extends ViewModel {

    private MutableLiveData<List<Member>> memberMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Member>> getMember() {
        return memberMutableLiveData;
    }

    public void setMember(String username, Context context) {
        ApiInterface Service;
        Call<List<Member>> Call;

        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getMember(username);
            Call.enqueue(new Callback<List<Member>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Member>> call, Response<List<Member>> response) {
                    List<Member> member = response.body();
                    memberMutableLiveData.postValue(member);
                }

                @Override
                public void onFailure(retrofit2.Call<List<Member>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
