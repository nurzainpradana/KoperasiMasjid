package com.nurzainpradana.koperasimasjid.view.updateprofile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.MemberPreference;
import com.nurzainpradana.koperasimasjid.view.registertwo.RegisterTwoAct;
import com.nurzainpradana.koperasimasjid.viewmodel.MemberViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.nurzainpradana.koperasimasjid.BuildConfig.BASE_URL;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener{

    private EditText edtFullname;
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtAddress;
    private EditText edtDateOfBirth;

    private ImageView ivPhotoProfile;

    private Button btnChooseDate;
    private Button btnUpdatePassword;
    private Button btnSaveProfile;

    private MemberViewModel memberViewModel;
    private MemberPreference memberPreference;
    private Member member;

    private Calendar myCalendar;
    Integer mYear;
    Integer mMonth;
    Integer mDay;
    SimpleDateFormat sdf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtFullname = view.findViewById(R.id.edt_name);
        edtUsername = view.findViewById(R.id.edt_username);
        edtPhoneNumber = view.findViewById(R.id.edt_no_phone);
        edtEmail = view.findViewById(R.id.edt_email);
        edtAddress = view.findViewById(R.id.edt_address);
        edtDateOfBirth = view.findViewById(R.id.edt_date_of_birth);
        ivPhotoProfile = view.findViewById(R.id.iv_register_photo);

        btnChooseDate = view.findViewById(R.id.btn_choose_date);
        btnUpdatePassword = view.findViewById(R.id.btn_update_password);
        btnSaveProfile = view.findViewById(R.id.btn_save_profile);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMemberPreference();
        memberViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MemberViewModel.class);
        memberViewModel.setMember(member.getmUsername(), getContext());

        memberViewModel.getMember().observe(getViewLifecycleOwner(), new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                setView(members.get(0));
            }
        });

        btnChooseDate.setOnClickListener(this);
    }

    public void setView(Member member) {
        edtFullname.setText(member.getmName());
        edtPhoneNumber.setText(member.getmNoPhone());
        edtUsername.setText(member.getmUsername());
        edtAddress.setText(member.getmAddress());
        edtEmail.setText(member.getmEmail());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        edtDateOfBirth.setText(ft.format(member.getmDateOfBirth()));
        String urlPhoto = BASE_URL + member.getmPhotoProfile();
        Picasso.get()
                .load(urlPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivPhotoProfile);
    }

    public Member getMemberPreference() {
        memberPreference = new MemberPreference(getContext());
        member = memberPreference.getMember();
        return member;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_date:
                myCalendar = Calendar.getInstance();
                mYear = myCalendar.get(Calendar.YEAR);
                mMonth = myCalendar.get(Calendar.MONTH);
                mDay = myCalendar.get(Calendar.DAY_OF_WEEK);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formatTanggal = "dd-MM-yyyy";
                        sdf = new SimpleDateFormat(formatTanggal, Locale.getDefault());
                        edtDateOfBirth.setText(sdf.format(myCalendar.getTime()));
                    }
                }, mYear, mMonth, mDay).show();
        }
    }
}