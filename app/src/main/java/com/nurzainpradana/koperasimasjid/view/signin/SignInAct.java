package com.nurzainpradana.koperasimasjid.view.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;
import com.nurzainpradana.koperasimasjid.view.registerone.RegisterOneAct;
import com.nurzainpradana.koperasimasjid.viewmodel.MemberViewModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInAct extends AppCompatActivity implements View.OnClickListener{
    private EditText edtSignInUsername;
    private EditText edtSignInPassword;

    private MemberViewModel memberViewModel;

    private String message;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btnSignIn = findViewById(R.id.btn_sign_in);
        TextView registerNow = findViewById(R.id.text_register_now);
        edtSignInUsername = findViewById(R.id.edt_sign_in_username);
        edtSignInPassword = findViewById(R.id.edt_sign_in_password);

        registerNow.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        memberViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MemberViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in) {
            String username = edtSignInUsername.getText().toString();
            String password = md5Java(edtSignInPassword.getText().toString());

            if (username.isEmpty()) {
                edtSignInUsername.setError(getString(R.string.not_null));
            } else {
                if (password.isEmpty()) {
                    edtSignInPassword.setError(getString(R.string.not_null));
                } else {
                    verification(username, password);
                }
            }
        } else if (v.getId() == R.id.text_register_now) {
            Intent goToRegisterOne = new Intent(SignInAct.this, RegisterOneAct.class);
            startActivity(goToRegisterOne);
        }
    }

    private void verification(String username, String password) {
        //Cek Verifikasi Username Password
        memberViewModel.setMember(username, getApplicationContext());
        memberViewModel.getMember().observe(this, members -> {
            if (username.equals(members.get(0).getmUsername())) {
                if ( password.equals(members.get(0).getmPassword())){

                    //set sharedpreference
                    SharedPreferences sf = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putString(username_key, edtSignInUsername.getText().toString());
                    editor.apply();

                    Intent goToHome = new Intent(SignInAct.this, MainActivity.class);
                    startActivity(goToHome);
                } else {
                    message = getString(R.string.wrong_password);
                }
            } else {
                message = getString(R.string.username_not_found);
            }
            if (message != null) {
                Toast.makeText(SignInAct.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Enkripsi Password
    public static String md5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));

            //merubah byte array ke dalam String Hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash)
            {
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(RegisterOneAct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }

}
