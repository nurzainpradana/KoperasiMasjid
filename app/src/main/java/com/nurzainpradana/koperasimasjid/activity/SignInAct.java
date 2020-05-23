package com.nurzainpradana.koperasimasjid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nurzainpradana.koperasimasjid.BuildConfig;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.model.ResultMember;
import com.nurzainpradana.koperasimasjid.viewmodel.ListMemberViewModel;
import com.nurzainpradana.koperasimasjid.viewmodel.MemberViewModel;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInAct extends AppCompatActivity implements View.OnClickListener{
    private Button btn_sign_in;
    private TextView register_now;
    private EditText edt_sign_in_username;
    private EditText edt_sign_in_password;
    private ListMemberViewModel listMemberViewModel;

    private MemberViewModel memberViewModel;

    private String message;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        register_now = findViewById(R.id.text_daftar_sekarang);
        edt_sign_in_username = findViewById(R.id.edt_sign_in_username);
        edt_sign_in_password = findViewById(R.id.edt_sign_in_password);


        register_now.setOnClickListener(this);
        btn_sign_in.setOnClickListener(this);

        memberViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MemberViewModel.class);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in) {
            String username = edt_sign_in_username.getText().toString();
            String password = md5Java(edt_sign_in_password.getText().toString());

            if (username.isEmpty()) {
                edt_sign_in_username.setError("Tidak Boleh Kosong");
            } else {
                if (password.isEmpty()) {
                    edt_sign_in_password.setError("Tidak Boleh Kosong");
                } else {
                    verification(username, password);
                }
            }
        } else if (v.getId() == R.id.text_daftar_sekarang) {
            Intent gotoregister1 = new Intent(SignInAct.this, RegisterOneAct.class);
            startActivity(gotoregister1);
        }
    }

    private void verification(String username, String password) {
        //Cek Verifikasi Username Password
        memberViewModel.setMember(username, getApplicationContext());
        memberViewModel.getMember().observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                if (username.equals(members.get(0).getmUsername())) {
                    if ( password.equals(members.get(0).getmPassword())){

                        //set sharedpreference
                        SharedPreferences sf = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putString(username_key, edt_sign_in_username.getText().toString());
                        editor.apply();

                        Intent goToHome = new Intent(SignInAct.this, MainActivity.class);
                        startActivity(goToHome);
                    } else {
                        message = "password salah";
                    }
                } else {
                    message = "Username tidak ditemukan";
                }
                if (message != null) {
                    Toast.makeText(SignInAct.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Enkripsi Password
    public static String md5Java(String message) {
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
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex)
        {
            Logger.getLogger(RegisterOneAct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }

}
