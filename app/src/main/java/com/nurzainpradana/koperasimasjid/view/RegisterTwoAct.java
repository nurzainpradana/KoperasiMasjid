package com.nurzainpradana.koperasimasjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterTwoAct extends AppCompatActivity {
    Button btn_register2_lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        btn_register2_lanjut = findViewById(R.id.btn_register2_lanjut);
        btn_register2_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosuccesregister = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                startActivity(gotosuccesregister);
            }
        });

    }
}
