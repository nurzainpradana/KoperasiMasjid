package com.nurzainpradana.koperasimasjid.view.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.EncryptMd5Java;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.util.SharePreferenceUtils;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;
import com.nurzainpradana.koperasimasjid.view.registerone.RegisterOneAct;
import com.nurzainpradana.koperasimasjid.viewmodel.UserViewModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInAct extends AppCompatActivity implements View.OnClickListener{
    private EditText edtSignInUsername;
    private EditText edtSignInPassword;

    private String result;

    private EncryptMd5Java encryptMd5Java;

    private UserViewModel userViewModel;

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

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in) {
            encryptMd5Java = new EncryptMd5Java();
            String username = edtSignInUsername.getText().toString();
            String password = encryptMd5Java.encrypt(encryptMd5Java.encrypt(edtSignInPassword.getText().toString()));

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
        String message = "";
        //Cek Verifikasi Username Password
        userViewModel.setUser(username, this.getBaseContext());
        userViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (username.equals(users.get(0).getmUsername())) {
                    if (password.equals(users.get(0).getmPassword())) {

                        SharePref sharePref = new SharePref(SignInAct.this.getBaseContext());
                        sharePref.setString(Const.USERNAME_KEY, users.get(0).getmUsername());

                        String message = getString(R.string.verification_success);
                        Toast.makeText(SignInAct.this, message, Toast.LENGTH_SHORT).show();

                        result = SignInAct.this.getString(R.string.verification_success);
                        Intent goToHome = new Intent(SignInAct.this, MainActivity.class);
                        SignInAct.this.startActivity(goToHome);
                    }
                    result = SignInAct.this.getString(R.string.wrong_password);

                } else {
                    result = SignInAct.this.getString(R.string.username_not_found);
                }
            }
        });
        if (result != null) {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        } else if(result == null) {
            Toast.makeText(this, "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
            result = null;
        }
    }
}
