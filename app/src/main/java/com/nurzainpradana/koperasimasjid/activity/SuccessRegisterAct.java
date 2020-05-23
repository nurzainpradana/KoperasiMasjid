package com.nurzainpradana.koperasimasjid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;

public class SuccessRegisterAct extends AppCompatActivity {
    Animation btt, ttb, app_splash;
    TextView text_success_register;
    ImageView image_success_register;
    Button btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //load animasi
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        //load elemen
        text_success_register = findViewById(R.id.text_sukses_register);
        btn_explore = findViewById(R.id.btn_explore);
        image_success_register = findViewById(R.id.image_success_register);

        //run animation
        text_success_register.startAnimation(ttb);
        image_success_register.startAnimation(app_splash);
        btn_explore.startAnimation(btt);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoberanda = new Intent(SuccessRegisterAct.this, MainActivity.class);
                startActivity(gotoberanda);
            }
        });




    }
}