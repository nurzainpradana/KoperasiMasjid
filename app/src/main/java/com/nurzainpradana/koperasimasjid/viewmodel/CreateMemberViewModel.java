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

import retrofit2.Callback;
import retrofit2.Response;

public class CreateMemberViewModel extends ViewModel {

    private MutableLiveData<Member> createMemberMutableLiveData = new MutableLiveData<>();

    public Member member;

    public void setCreateMember (Context context, Member member){

        ApiInterface Service;
        retrofit2.Call<ResultMember> Call;
        Service = Api.getApi().create(ApiInterface.class);
        Call = Service.insertMember(member.getmName(), member.getmNoPhone(), member.getmUsername(), member.getmPassword(), member.getmEmail(), member.getmAddress(), member.getmDateOfBirth().toString(), member.getmPhotoProfile());
        Call.enqueue(new Callback<ResultMember>() {
            @Override
            public void onResponse(retrofit2.Call<ResultMember> call, Response<ResultMember> response) {
                if (response.body() != null) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultMember> call, Throwable t) {
                t.printStackTrace();
                Log.e("error", String.valueOf(t));
                Toast.makeText(context, "Jaringan Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
