package com.nurzainpradana.koperasimasjid.view.updateprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;

public class UpdateProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        View topBar = findViewById(R.id.top_bar_layout);
        titleBar = topBar.findViewById(R.id.title_bar);
        btnBack = topBar.findViewById(R.id.btn_back);

        titleBar.setText(R.string.update_profile);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
                finish();
            }
        });
    }


}