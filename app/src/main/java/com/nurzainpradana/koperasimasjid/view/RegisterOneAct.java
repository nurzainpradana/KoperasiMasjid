package com.nurzainpradana.koperasimasjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterOneAct extends AppCompatActivity {
    Button btn_register1_lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_register1_lanjut = findViewById(R.id.btn_register1_lanjut);

        btn_register1_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoverifikasi = new Intent(RegisterOneAct.this, VerificationAct.class);
                startActivity(gotoverifikasi);
            }
        });

    }
}
