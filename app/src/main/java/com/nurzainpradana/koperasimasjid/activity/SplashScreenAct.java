package com.nurzainpradana.koperasimasjid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;

public class SplashScreenAct extends AppCompatActivity {
    Animation app_splash, btt;
    ImageView logo_koperasi, masjid, muslimhandshake;
    TextView judul_koperasi, nama_koperasi, alamat_koperasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //load elemen
        logo_koperasi = findViewById(R.id.logo_koperasi);
        judul_koperasi = findViewById(R.id.judul_koperasi);
        nama_koperasi = findViewById(R.id.nama_koperasi);
        alamat_koperasi = findViewById(R.id.alamat_koperasi);
        masjid = findViewById(R.id.background_masjid);
        muslimhandshake = findViewById(R.id.muslimhandshake);

        runAnimation();
    }

    private void runAnimation() {
        //Load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
        logo_koperasi.startAnimation(app_splash);
        judul_koperasi.startAnimation(btt);
        nama_koperasi.startAnimation(btt);
        alamat_koperasi.startAnimation(btt);

        masjid.setAnimation(app_splash);
        muslimhandshake.setAnimation(app_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent gotosign = new Intent(SplashScreenAct.this, SignInAct.class);
            startActivity(gotosign);
            finish();
        }, 2000); //1000milisecon = 1 secon
    }
}
