package com.nurzainpradana.koperasimasjid.view.updatepassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.view.profile.ProfileFragment;

public class UpdatePasswordAct extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnBack;
    private TextView titleBar;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtReEnterPassword;
    private Button btnUpdatePassword;

    private User user;

    private String oldPasword;
    private String newPasword;
    private String reEnterPasword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        View topBar = findViewById(R.id.top_bar_layout);
        titleBar = topBar.findViewById(R.id.title_bar);
        btnBack = topBar.findViewById(R.id.btn_back);

        titleBar.setText(R.string.update_password);
        btnBack.setOnClickListener(this);

        edtOldPassword = findViewById(R.id.edt_old_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtReEnterPassword = findViewById(R.id.edt_password_confirmation);

        btnUpdatePassword = findViewById(R.id.btn_update_password);

        btnUpdatePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back :
                Intent goToProfile = new Intent(UpdatePasswordAct.this, ProfileFragment.class);
                startActivity(goToProfile);
                finish();
                break;

            case R.id.btn_update_password :
                if (edtOldPassword.getText() == null) {
                    edtOldPassword.setError("Kolom harus diisi");
                } else if (edtNewPassword.getText() == null) {
                    edtOldPassword.setError("Kolom harus diisi");
                } else if (edtReEnterPassword.getText() == null) {
                    edtReEnterPassword.setError("Kolom harus diisi");
                } else if (edtNewPassword.getText() != null && edtOldPassword.getText() != null && edtReEnterPassword.getText() != null) {
                    oldPasword = edtOldPassword.getText().toString();
                    newPasword = edtNewPassword.getText().toString();
                    reEnterPasword = edtReEnterPassword.getText().toString();
                }
        }
    }
}
