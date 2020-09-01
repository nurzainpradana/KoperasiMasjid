package com.nurzainpradana.koperasimasjid.view.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.view.signin.SignInAct;
import com.nurzainpradana.koperasimasjid.view.updateprofile.UpdateProfileActivity;
import com.nurzainpradana.koperasimasjid.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.nurzainpradana.koperasimasjid.util.Const.IMAGE_USER_URL;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.btn_log_out)
    Button btnLogOut;

    public User user;

    private TextView tvProfileName;
    private TextView tvProfileAddress;
    private TextView tvProfileNoPhone;
    private TextView tvProfileEmail;
    private TextView tvProfileBirthdate;
    private TextView tvProfilUsername;
    private CircleImageView ivProfilePicture;
    private Button btnEditProfile;

    SharePref sharePref;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
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

        UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        if (getContext() != null) {
            new Const();
            sharePref = new SharePref(getContext());
            String username = sharePref.getString(Const.USERNAME_KEY);
            userViewModel.setUser(username, getContext());
        }
        userViewModel.getUser().observe(getViewLifecycleOwner(), this::setView);
    }

    private void setView(List<User> users) {
        tvProfileName.setText(users.get(0).getmName().toUpperCase());
        tvProfileAddress.setText(users.get(0).getmAddress());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        tvProfileBirthdate.setText(ft.format(users.get(0).getmDateOfBirth()));
        tvProfileEmail.setText(users.get(0).getmEmail());
        tvProfileNoPhone.setText(users.get(0).getmNoPhone());
        tvProfilUsername.setText(users.get(0).getmUsername());

        String urlPhoto = IMAGE_USER_URL + users.get(0).getmPhotoProfile();
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
        btnLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_profile:
                Intent goToUpdateProfile = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(goToUpdateProfile);
                break;

            case R.id.btn_log_out:
                sharePref = new SharePref(requireContext());
                sharePref.clearSharePref(Const.USERNAME_KEY);
                sharePref.clearSharePref(Const.ID_USER_KEY);

                Intent goToSignIn = new Intent(getActivity(), SignInAct.class);
                startActivity(goToSignIn);
                getActivity().finish();
                break;
        }
    }


}


