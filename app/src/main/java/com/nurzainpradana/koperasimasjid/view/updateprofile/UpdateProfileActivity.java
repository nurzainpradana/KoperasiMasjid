package com.nurzainpradana.koperasimasjid.view.updateprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.UsernamePreference;
import com.nurzainpradana.koperasimasjid.util.UploadImage;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;
import com.nurzainpradana.koperasimasjid.view.verification.VerificationAct;
import com.nurzainpradana.koperasimasjid.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.nurzainpradana.koperasimasjid.BuildConfig.BASE_URL;
import static com.nurzainpradana.koperasimasjid.util.Const.IMGPATH;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtFullname;
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtAddress;
    private EditText edtDateOfBirth;

    private ImageView ivPhotoProfile;

    private UserViewModel userViewModel;
    private UsernamePreference usernamePreference;
    private User user;

    RelativeLayout mLayout;

    private Calendar calendar;
    private SimpleDateFormat sdf;

    String imgPath;
    String fileName;
    String urlPhoto;
    String urlPhotoProfile;
    
    ImageView btnBack;

    public UpdateProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        View topBar = findViewById(R.id.top_bar_layout);
        TextView titleBar = topBar.findViewById(R.id.title_bar);
        btnBack = topBar.findViewById(R.id.btn_back);

        titleBar.setText(R.string.update_profile);

        edtFullname = findViewById(R.id.edt_name);
        edtUsername = findViewById(R.id.edt_username);
        edtPhoneNumber = findViewById(R.id.edt_no_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth);
        ivPhotoProfile = findViewById(R.id.iv_register_photo);

        Button btnChooseDate = findViewById(R.id.btn_choose_date);
        Button btnUpdatePassword = findViewById(R.id.btn_update_password);
        Button btnSaveProfile = findViewById(R.id.btn_save_profile);
        Button btnAddPhoto = findViewById(R.id.btn_add_photo);

        btnChooseDate.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnSaveProfile.setOnClickListener(this);
        btnUpdatePassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);


        getUsernamePreference();

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        userViewModel.setUser(user.getmUsername(), getApplicationContext());
        userViewModel.getUser().observe(this, users -> setView(users.get(0)));

    }


    public void setView(User user) {
        edtFullname.setText(user.getmName());
        edtPhoneNumber.setText(user.getmNoPhone());
        edtUsername.setText(user.getmUsername());
        edtAddress.setText(user.getmAddress());
        edtEmail.setText(user.getmEmail());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        edtDateOfBirth.setText(ft.format(user.getmDateOfBirth()));
        String urlPhoto = BASE_URL + IMGPATH + user.getmPhotoProfile();
        Picasso.get()
                .load( urlPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivPhotoProfile);

        urlPhotoProfile = user.getmPhotoProfile();
    }

    public void getUsernamePreference() {
        usernamePreference = new UsernamePreference(getApplicationContext());
        usernamePreference.getUsernameSF();
    }

    public void setUsernamePreference(String username) {
        usernamePreference = new UsernamePreference(getApplicationContext());
        usernamePreference.setUsernameSF(username);
    }

    private void showCameraPreview() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Permission is already availbale, start camera provide
            loadImagefromGallery();
        } else {
            //Permission is missing and must be request
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        //Permission has not been granted and must be request
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(mLayout, R.string.camera_access_required, Snackbar.LENGTH_INDEFINITE).setAction("OK", v -> {
                //request permission
                ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new Const().PERMISSION_REQUEST_CAMERA);
            }).show();
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new Const().PERMISSION_REQUEST_CAMERA);
        }
    }

    public void loadImagefromGallery() {
        //create intent to open image application like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Start intent
        startActivityForResult(galleryIntent,
                new Const().RESULT_LOAD_IMAGE);
    }

    private void updateMember() {
        if (fileName == null){
            urlPhoto = urlPhotoProfile;
        } else {
            UploadImage uploadImage = new UploadImage(imgPath, fileName);
            uploadImage.uploadImage();

            uploadImage = new UploadImage(urlPhotoProfile);
            uploadImage.removeImage();

            urlPhoto = fileName;
        }
        Context context = getApplicationContext();
        if (context!=null) {

            userViewModel.setUpdateMember(context, user);
            setUsernamePreference(user.getmUsername());
        }
    }

    public User setMember() {
        User mUser = new User();
        mUser.setmName(edtFullname.getText().toString());
        mUser.setmNoPhone(edtPhoneNumber.getText().toString());
        mUser.setmUsername(edtUsername.getText().toString());
        mUser.setmAddress(edtAddress.getText().toString());
        mUser.setmEmail(edtEmail.getText().toString());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date birthDate = sdf.parse(edtDateOfBirth.getText().toString());
            mUser.setmDateOfBirth(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mUser;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                Intent goToMainActivity = new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
                finish();
                break;

            case R.id.btn_choose_date:
                calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_WEEK);
                new DatePickerDialog(getApplicationContext(), (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String formatTanggal = "dd-MM-yyyy";
                    sdf = new SimpleDateFormat(formatTanggal, Locale.getDefault());
                    edtDateOfBirth.setText(sdf.format(calendar.getTime()));
                }, mYear, mMonth, mDay).show();
                break;

            case R.id.btn_add_photo:
                showCameraPreview();
                break;

            case R.id.btn_save_profile:
                user = setMember();
                updateMember();
                setUsernamePreference(user.getmUsername());
                break;

            case R.id.btn_update_password:
                user = setMember();
                updateMember();
                Intent goToVerification = new Intent(getApplicationContext(), VerificationAct.class);
                goToVerification.putExtra(new Const().EXTRA_TYPE, new Const().UPDATE_KEY);
                startActivity(goToVerification);
                break;
        }
    }
}