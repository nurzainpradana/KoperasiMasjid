package com.nurzainpradana.koperasimasjid.view.registerone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.view.verification.VerificationAct;
import com.nurzainpradana.koperasimasjid.viewmodel.ListMemberViewModel;

import java.util.List;

import static com.nurzainpradana.koperasimasjid.util.EncryptMd5Java.encryptMd5Java;

public class RegisterOneAct extends AppCompatActivity {

    Button btnRegisterOneNext;
    EditText edtName;
    EditText edtNoPhone;
    EditText edtUsername;
    EditText edtPassword;

    ListMemberViewModel listMemberViewModel;
    List<Member> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        listMemberViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListMemberViewModel.class);
        listMemberViewModel.setListMember();
        listMemberViewModel.getListMember().observe(this, resultMember -> list = resultMember.getmResultMember());

        btnRegisterOneNext = findViewById(R.id.btn_register_one_next);
        edtName = findViewById(R.id.edt_name);
        edtNoPhone = findViewById(R.id.edt_no_phone);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        btnRegisterOneNext.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String name = edtName.getText().toString();
            String noPhone = getString(R.string.phone_code) + edtNoPhone.getText().toString();
            String password = edtPassword.getText().toString();

            Member member = new Member();
            member.setmName(name);
            member.setmNoPhone(noPhone);
            member.setmUsername(username);
            member.setmPassword(encryptMd5Java(encryptMd5Java(password)));

            if (!checkUsername(username)) {
                Intent goToVerification = new Intent(RegisterOneAct.this, VerificationAct.class);
                goToVerification.putExtra(VerificationAct.EXTRA_MEMBER, member);
                startActivity(goToVerification);
            } else if (checkUsername(username)){
                Toast.makeText(RegisterOneAct.this, getString(R.string.username_already_registered), Toast.LENGTH_SHORT).show();
                edtUsername.setError(getString(R.string.username_already_registered));
                edtUsername.requestFocus();
            }
        });
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

}
