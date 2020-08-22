package com.nurzainpradana.koperasimasjid.view.updateprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.nurzainpradana.koperasimasjid.BuildConfig;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.ResultUser;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.util.UploadImage;
import com.nurzainpradana.koperasimasjid.view.detail.DetailProduct;
import com.nurzainpradana.koperasimasjid.view.main.MainActivity;
import com.nurzainpradana.koperasimasjid.view.profile.ProfileFragment;
import com.nurzainpradana.koperasimasjid.view.registertwo.RegisterTwoAct;
import com.nurzainpradana.koperasimasjid.view.updatepassword.UpdatePasswordAct;
import com.nurzainpradana.koperasimasjid.view.verification.VerificationAct;
import com.nurzainpradana.koperasimasjid.viewmodel.ListUserViewModel;
import com.nurzainpradana.koperasimasjid.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.nurzainpradana.koperasimasjid.util.NetworkUtility.isNetworkConnected;
import static com.nurzainpradana.koperasimasjid.util.Const.IMAGE_USER_URL;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtFullname;
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtAddress;
    private EditText edtDateOfBirth;

    private ImageView ivPhotoProfile;

    UserViewModel userViewModel;
    User user;

    RelativeLayout mLayout;

    private Calendar calendar;
    private SimpleDateFormat sdf;

    String imgPath;
    String fileName;
    String urlPhoto;
    String urlPhotoProfile;
    
    ImageView btnBack;

    ListUserViewModel listUserViewModel;
    List<User> list;

    int id_user;

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

        if (isNetworkConnected(this)) {
            userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
            SharePref sharePref = new SharePref(getApplicationContext());
            String username = sharePref.getString(Const.USERNAME_KEY);

            userViewModel.setUser(username, getApplicationContext());
            userViewModel.getUser().observe(this, users -> setView(users.get(0)));


        } else {
            Toast.makeText(this, getString(R.string.network_not_connect), Toast.LENGTH_SHORT).show();
        }

    }


    public void setView(User user) {
        edtFullname.setText(user.getmName());
        edtPhoneNumber.setText(user.getmNoPhone());
        edtUsername.setText(user.getmUsername());
        edtAddress.setText(user.getmAddress());
        edtEmail.setText(user.getmEmail());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        edtDateOfBirth.setText(ft.format(user.getmDateOfBirth()));
        String urlPhoto = IMAGE_USER_URL + user.getmPhotoProfile();
        Picasso.get()
                .load( urlPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivPhotoProfile);

        id_user = user.getmIdUser();
        urlPhotoProfile = user.getmPhotoProfile();
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
                ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new Const().PERMISSION_REQUEST_CAMERA);
            }).show();
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new Const().PERMISSION_REQUEST_CAMERA);
        }
    }

    public void loadImagefromGallery() {
        //create intent to open image application like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Start intent
        startActivityForResult(galleryIntent,
                new Const().RESULT_LOAD_IMAGE);
    }

    //When image selected from gallery


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //when image is picked
            if (requestCode == new Const().RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                //get image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //Get the cursor
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                }

                //set image in imageView
                ivPhotoProfile.setImageBitmap(BitmapFactory.decodeFile(imgPath));

                //get the image's filename
                /* String[] fileNameSegments = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                 */
                Random random = new Random();
                fileName = getString(R.string.user_image_filename) + random.nextInt(999999) + ".png";
            } else {
                Toast.makeText(this, "You Haven't picked image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }



    private void updateMember(User user) {
        if (fileName == null){
            urlPhoto = urlPhotoProfile;
        } else {
            UploadImage uploadImage = new UploadImage(imgPath, fileName);
            uploadImage.uploadImage();

            uploadImage = new UploadImage(urlPhotoProfile);
            uploadImage.removeImage();
            urlPhoto = fileName;
        }
        user.setmPhotoProfile(urlPhoto);

        listUserViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListUserViewModel.class);
        listUserViewModel.setListUser();
        listUserViewModel.getListUser().observe(this, new Observer<ResultUser>() {
            @Override
            public void onChanged(ResultUser resultUser) {

            }
        });

        userViewModel.checkUsername(this, user.getmUsername());
        userViewModel.getUserMutableLiveData2().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                list = users;
                if (list.get(0).getmUsername() == null || list.get(0).getmUsername().equals(user.getmUsername())){
                    SharePref sharePref = new SharePref(getApplicationContext());
                    sharePref.setString(Const.USERNAME_KEY, user.getmUsername());
                    userViewModel.setUpdateMember(getApplicationContext(), user);
                } else {
                    edtUsername.setError(getString(R.string.username_already_registered));
                }
            }
        });
    }

    public User setMember() {
        User mUser = new User();
        mUser.setmIdUser(id_user);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent goToMainActivity = new Intent(UpdateProfileActivity.this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
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
                new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formatTanggal = "dd-MM-yyyy";
                        sdf = new SimpleDateFormat(formatTanggal, Locale.getDefault());
                        edtDateOfBirth.setText(sdf.format(calendar.getTime()));
                    }
                }, mYear, mMonth, mDay).show();
                break;

            case R.id.btn_add_photo:
                showCameraPreview();
                break;

            case R.id.btn_save_profile:
                user = setMember();
                updateMember(user);
                break;

            case R.id.btn_update_password:
                user = setMember();
                updateMember(user);
                /*
                Intent goToVerification = new Intent(getApplicationContext(), VerificationAct.class);
                goToVerification.putExtra(new Const().EXTRA_TYPE, new Const().UPDATE_KEY);
                startActivity(goToVerification);

                 */
                Intent goToVerification = new Intent(getApplicationContext(), UpdatePasswordAct.class);
                goToVerification.putExtra(new Const().EXTRA_TYPE, new Const().UPDATE_KEY);
                startActivity(goToVerification);
                break;
        }
    }
}