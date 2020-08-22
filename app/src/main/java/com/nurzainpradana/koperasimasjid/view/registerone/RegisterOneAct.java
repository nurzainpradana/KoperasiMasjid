package com.nurzainpradana.koperasimasjid.view.registerone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.ResultUser;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.EncryptMd5Java;
import com.nurzainpradana.koperasimasjid.view.registertwo.RegisterTwoAct;
import com.nurzainpradana.koperasimasjid.view.verification.VerificationAct;
import com.nurzainpradana.koperasimasjid.viewmodel.ListUserViewModel;

import java.util.List;

public class RegisterOneAct extends AppCompatActivity implements View.OnClickListener {

    Button btnRegisterOneNext;
    EditText edtName;
    EditText edtNoPhone;
    EditText edtUsername;
    EditText edtPassword;

    ListUserViewModel listUserViewModel;
    List<User> list;

    EncryptMd5Java encryptMd5Java = new EncryptMd5Java();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        listUserViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListUserViewModel.class);
        listUserViewModel.setListUser();
        listUserViewModel.getListUser().observe(this, new Observer<ResultUser>() {
            @Override
            public void onChanged(ResultUser resultUser) {
                list = resultUser.getmResultUser();
            }
        });

        btnRegisterOneNext = findViewById(R.id.btn_register_one_next);
        edtName = findViewById(R.id.edt_name);
        edtNoPhone = findViewById(R.id.edt_no_phone);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        btnRegisterOneNext.setOnClickListener(this);
    }

    private boolean checkUsername(String username) {
        boolean isAlready = false;
        if (list != null) {
            if (list.size() > 0) {
                for (int i = 0; i < list.toArray().length; i++) {
                    boolean checkUsername = (username.equals(list.get(i).getmUsername()));
                    //Jika username sudah terdaftar
                    if (checkUsername)
                        isAlready = true;
                }
            }
        }
        return isAlready;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register_one_next) {
            String username = edtUsername.getText().toString();
            String name = edtName.getText().toString();
            String noPhone = getString(R.string.phone_code) + edtNoPhone.getText().toString();
            String password = edtPassword.getText().toString();

            User user = new User();
            user.setmName(name);
            user.setmNoPhone(noPhone);
            user.setmUsername(username);
            user.setmPassword(encryptMd5Java.encrypt(encryptMd5Java.encrypt(password)));

            if (!checkUsername(username)) {
                Intent goToVerification = new Intent(RegisterOneAct.this, VerificationAct.class);
                /*
                goToVerification.putExtra(new Const().EXTRA_USER, user);
                goToVerification.putExtra(new Const().EXTRA_TYPE, new Const().REGISTRATION_KEY);
                startActivity(goToVerification);
                 */
                Intent intent = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(intent);
            } else if (checkUsername(username)) {
                Toast.makeText(RegisterOneAct.this, getString(R.string.username_already_registered), Toast.LENGTH_SHORT).show();
                edtUsername.setError(getString(R.string.username_already_registered));
                edtUsername.requestFocus();
            }
        }

    }
}
