package com.nurzainpradana.koperasimasjid.view.sucessregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;

public class SuccessRegisterAct extends AppCompatActivity {
    Animation btt, ttb, appSplash;
    TextView textSuccessRegister;
    ImageView imageSuccessRegister;
    Button btnExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //load animasi
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        //load elemen
        textSuccessRegister = findViewById(R.id.success_register_text);
        btnExplore = findViewById(R.id.btn_explore);
        imageSuccessRegister = findViewById(R.id.image_success_register);

        runAnimation();
        btnExplore.setOnClickListener(v -> {
            Intent goToMain = new Intent(SuccessRegisterAct.this, MainActivity.class);
            startActivity(goToMain);
        });
    }

    private void runAnimation() {
        //run animation
        textSuccessRegister.startAnimation(ttb);
        imageSuccessRegister.startAnimation(appSplash);
        btnExplore.startAnimation(btt);
    }
}