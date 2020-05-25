package com.nurzainpradana.koperasimasjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignInAct extends AppCompatActivity {
    Button btn_sign_in;
    TextView text_daftar_sekarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        text_daftar_sekarang = findViewById(R.id.text_daftar_sekarang);

        text_daftar_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister1 = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gotoregister1);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomain = new Intent(SignInAct.this, MainActivity.class);
                startActivity(gotomain);
                finish();
            }
        });
    }
}
