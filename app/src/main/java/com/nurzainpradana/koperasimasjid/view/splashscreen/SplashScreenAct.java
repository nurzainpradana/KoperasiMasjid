package com.nurzainpradana.koperasimasjid.view.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;
import com.nurzainpradana.koperasimasjid.view.signin.SignInAct;

public class SplashScreenAct extends AppCompatActivity {
    Animation appSplash;
    Animation btt;
    ImageView organizationLogo;
    ImageView mosque;
    ImageView muslimHandshake;
    TextView organizationName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //load elemen
        organizationLogo = findViewById(R.id.organization_logo);
        organizationName = findViewById(R.id.organization_name);
        mosque = findViewById(R.id.background_mosque);
        muslimHandshake = findViewById(R.id.background_muslim_handshake);
        runAnimation();
    }

    private void runAnimation() {
        //Load animation
        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
        organizationLogo.startAnimation(appSplash);
        organizationName.startAnimation(btt);

        mosque.setAnimation(appSplash);
        muslimHandshake.setAnimation(appSplash);

        Handler handler = new Handler();

        SharePref sharePref = new SharePref(getApplicationContext());
        String username = sharePref.getString(Const.ID_USER_KEY);
        if (username != null){
            handler.postDelayed(() -> {
                Intent gotomain = new Intent(SplashScreenAct.this, MainActivity.class);
                startActivity(gotomain);
                finish();
            }, 2000); //1000milisecon = 1 secon
        } else {
            handler.postDelayed(() -> {
                Intent gotosign = new Intent(SplashScreenAct.this, SignInAct.class);
                startActivity(gotosign);
                finish();
            }, 2000); //1000milisecon = 1 secon
        }
    }
}
