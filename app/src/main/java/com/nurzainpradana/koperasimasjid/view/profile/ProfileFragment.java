package com.nurzainpradana.koperasimasjid.view.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.util.MemberPreference;
import com.nurzainpradana.koperasimasjid.view.updateprofile.UpdateProfileActivity;
import com.nurzainpradana.koperasimasjid.viewmodel.MemberViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nurzainpradana.koperasimasjid.BuildConfig.BASE_URL;
import static com.nurzainpradana.koperasimasjid.util.Const.IMGPATH;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private MemberViewModel memberViewModel;

    public Member member;
    public MemberPreference memberPreference;

    private TextView tvProfileName;
    private TextView tvProfileAddress;
    private TextView tvProfileNoPhone;
    private TextView tvProfileEmail;
    private TextView tvProfileBirthdate;
    private TextView tvProfilUsername;
    private CircleImageView ivProfilePicture;
    private Button btnUpdatePassword, btnEditProfile;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEditProfile = view.findViewById(R.id.btn_edit_profile);

        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvProfileAddress = view.findViewById(R.id.tv_profile_address);
        tvProfileEmail = view.findViewById(R.id.tv_profile_email);
        tvProfileBirthdate = view.findViewById(R.id.tv_profile_birthdate);
        tvProfileNoPhone = view.findViewById(R.id.tv_profile_nophone);
        tvProfilUsername = view.findViewById(R.id.tv_profile_username);
        ivProfilePicture = view.findViewById(R.id.iv_profile_frame_photo);

        if (getContext() != null) {
            memberPreference = new MemberPreference(getContext());
            member = memberPreference.getMember();
            if (member != null) {
                String username = member.getmUsername();
                memberViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MemberViewModel.class);
                memberViewModel.setMember(username, getContext());
            }
        }

        memberViewModel.getMember().observe(getViewLifecycleOwner(), new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                ProfileFragment.this.setView(members);
            }
        });
    }

    private void setView(List<Member> members) {
        tvProfileName.setText(members.get(0).getmName().toUpperCase());
        tvProfileAddress.setText(members.get(0).getmAddress());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        tvProfileBirthdate.setText(ft.format(members.get(0).getmDateOfBirth()));
        tvProfileEmail.setText(members.get(0).getmEmail());
        tvProfileNoPhone.setText(members.get(0).getmNoPhone());
        tvProfilUsername.setText(members.get(0).getmUsername());

        String urlPhoto = BASE_URL + IMGPATH + members.get(0).getmPhotoProfile();
        Picasso.get()
                .load(urlPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivProfilePicture);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnEditProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_edit_profile) {
            Intent goToUpdateProfile = new Intent(getActivity(), UpdateProfileActivity.class);
            startActivity(goToUpdateProfile);
        }
    }
}
