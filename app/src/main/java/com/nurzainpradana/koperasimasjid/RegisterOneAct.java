package com.nurzainpradana.koperasimasjid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.model.Result;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOneAct extends AppCompatActivity {

    Button btn_register1_lanjut;
    EditText edtName, edtNoPhone, edtUsername, edtPassword;

    List<Member> List = new ArrayList<>();
    ApiInterface Service;
    Call<Result> Call;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        getMember();

        btn_register1_lanjut = findViewById(R.id.btn_register1_lanjut);
        edtName = findViewById(R.id.edt_name);
        edtNoPhone = findViewById(R.id.edt_no_phone);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);



        btn_register1_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member member = new Member();
                member.setmName(edtName.getText().toString());
                member.setmNoPhone(edtNoPhone.getText().toString());
                member.setmUsername(edtUsername.getText().toString());
                member.setmPassword(md5Java(edtPassword.getText().toString()));

                for (int i = 0; i < List.toArray().length; i++) {
                    Boolean checkUsername = (edtUsername.getText().toString()).equals(List.get(i).getmUsername());
                    Boolean checkNoPhone = (edtNoPhone.getText().toString()).equals(List.get(i).getmNoPhone());
                    if (checkUsername) {
                        Toast.makeText(RegisterOneAct.this, "Username Sudah terdaftar", Toast.LENGTH_SHORT).show();
                        edtUsername.setError("Username Sudah Terdaftar");
                        edtUsername.requestFocus();
                    } else {
                        Intent gotoregistertwo = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                        gotoregistertwo.putExtra(RegisterTwoAct.EXTRA_MEMBER, member);
                        startActivity(gotoregistertwo);
                    }
                }
            }
        });
    }

    private void getMember() {
        Service = Api.getApi().create(ApiInterface.class);
        Call = Service.getMember();
        Call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                List.clear();
                if (response.body() != null) {
                    List = response.body().getmResultMember();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                Log.e("Error Bosque", t.toString());
            }
        });
    }

    public static String md5Java(String message)
    {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            //merubah byte array ke dalam String Hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash)
            {
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(RegisterOneAct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(RegisterOneAct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}
