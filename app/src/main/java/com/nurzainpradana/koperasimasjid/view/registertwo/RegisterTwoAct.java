package com.nurzainpradana.koperasimasjid.view.registertwo;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.User;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.util.UploadImage;
import com.nurzainpradana.koperasimasjid.view.sucessregister.SuccessRegisterAct;
import com.nurzainpradana.koperasimasjid.viewmodel.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.nurzainpradana.koperasimasjid.util.Const.ID_USER_KEY;
import static com.nurzainpradana.koperasimasjid.util.Const.USERNAME_KEY;


public class RegisterTwoAct extends AppCompatActivity implements View.OnClickListener{

    Button btnRegisterTwoNext;
    Button btnAddPhoto;
    Button btnChooseDate;
    EditText edtEmail;
    EditText edtAddress;
    EditText edtDateOfBirth;
    ImageView ivRegisterPhoto;
    ProgressBar progressBar;

    int id;
    String email;
    String address;
    String name;
    String username;
    String password;
    String noPhone;
    String dateOfBirth;
    String imgPath;
    String fileName;
    Integer mYear;
    Integer mMonth;
    Integer mDay;

    SimpleDateFormat sdf;
    Calendar myCalendar;

    RelativeLayout mLayout;

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth);
        ivRegisterPhoto = findViewById(R.id.iv_register_photo);
        progressBar = findViewById(R.id.loading);

        btnAddPhoto = findViewById(R.id.btn_add_photo);
        btnChooseDate = findViewById(R.id.btn_choose_date);
        btnRegisterTwoNext = findViewById(R.id.btn_register_two_next);

        Random random = new Random();
        id = random.nextInt(99999);

        User user = getIntent().getParcelableExtra(new Const().EXTRA_USER);
        if (user != null) {
            name = user.getmName();
            username = user.getmUsername();
            password = user.getmPassword();
            noPhone = user.getmNoPhone();
        }
        myCalendar = Calendar.getInstance();
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH);
        mDay = myCalendar.get(Calendar.DAY_OF_WEEK);

        btnChooseDate.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnRegisterTwoNext.setOnClickListener(this);
    }

    public void saveMember(User mUser){

        UploadImage uploadImage = new UploadImage(imgPath, fileName);
        uploadImage.uploadImage();

        userViewModel = new UserViewModel();
        userViewModel.setCreateUser(getApplicationContext(), mUser);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Begin_include(onRequestPermissionResult)
        if (requestCode == new Const().PERMISSION_REQUEST_CAMERA) {
            //request for camera permission
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission has been granted. start camera preview action
                loadImagefromGallery();
            } else {
                //permission request was denied
                Log.e("Error", "Permission request was denied");
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
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
                ActivityCompat.requestPermissions(RegisterTwoAct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new Const().PERMISSION_REQUEST_CAMERA);
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
        startActivityForResult(galleryIntent, new Const().RESULT_LOAD_IMAGE);
    }

    //when image is selected from gallery
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
                ivRegisterPhoto.setImageBitmap(BitmapFactory.decodeFile(imgPath));

                /*//get the image's filename
                String[] fileNameSegments = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                 */
                Random random = new Random();
                fileName = getString(R.string.user_image_filename) + random.nextInt(999999) +".png";
            } else {
                Toast.makeText(this, "You Haven't picked image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dissmiss the progress bar when appliaction is closed
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_date:
                new DatePickerDialog(RegisterTwoAct.this, (view, year, month, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String formatTanggal = "dd-MM-yyyy";
                    sdf = new SimpleDateFormat(formatTanggal, Locale.getDefault());
                    edtDateOfBirth.setText(sdf.format(myCalendar.getTime()));
                }, mYear, mMonth, mDay).show();
                break;

            case R.id.btn_add_photo:
                showCameraPreview();
                break;

            case R.id.btn_register_two_next:
                if (edtEmail.getText().toString().isEmpty()) {
                    edtEmail.setError(getString(R.string.not_null));
                } else if (edtAddress.getText().toString().isEmpty()) {
                    edtAddress.setError(getString(R.string.not_null));
                } else if (edtDateOfBirth.getText().toString().isEmpty()) {
                    edtDateOfBirth.setError(getString(R.string.not_null));
                } else {
                    email = edtEmail.getText().toString();
                    address = edtAddress.getText().toString();
                    dateOfBirth = edtDateOfBirth.getText().toString();
                    try {
                        User mUser = new User();
                        mUser.setmIdUser(id);
                        mUser.setmName(name);
                        mUser.setmNoPhone(noPhone);
                        mUser.setmUsername(username);
                        mUser.setmPassword(password);
                        mUser.setmEmail(email);
                        mUser.setmAddress(address);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date birthDate = sdf.parse(dateOfBirth);
                        mUser.setmDateOfBirth(birthDate);
                        mUser.setmPhotoProfile(fileName);
                        saveMember(mUser);

                        SharePref sharePref = new SharePref(getApplicationContext());
                        sharePref.clearSharePref(ID_USER_KEY);
                        sharePref.clearSharePref(USERNAME_KEY);

                        sharePref.setInt(ID_USER_KEY, mUser.getmIdUser());
                        sharePref.setString(Const.USERNAME_KEY, mUser.getmUsername());

                        if (sharePref.getInt(ID_USER_KEY) != null ){
                            Intent gotoSuccessRegister = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                            startActivity(gotoSuccessRegister);
                            finish();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
