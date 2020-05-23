package com.nurzainpradana.koperasimasjid.view.registertwo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.view.sucessregister.SuccessRegisterAct;
import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.Member;
import com.nurzainpradana.koperasimasjid.model.ResultMember;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTwoAct extends AppCompatActivity {
    public static final String EXTRA_MEMBER = "extra_member";
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static int RESULT_LOAD_IMAGE = 1;

    Button btnRegisterTwoNext;
    Button btnAddPhoto;
    EditText edtEmail;
    EditText edtAddress;
    EditText edtDateOfBirth;
    ImageView ivRegisterPhoto;
    ProgressBar progressBar;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    String email;
    String address;
    String name;
    String username;
    String password;
    String noPhone;
    String dateOfBirth;
    String encodedString;
    String imgPath;
    String fileName;
    Integer mYear;
    Integer mMonth;
    Integer mDay;

    SimpleDateFormat sdf;
    Bitmap bitmap;
    Calendar myCalendar;
    ApiInterface Service;
    RequestParams params = new RequestParams();
    Call<ResultMember> Call;
    RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth);
        ivRegisterPhoto = findViewById(R.id.iv_register_photo);
        btnAddPhoto = findViewById(R.id.btn_add_photo);
        progressBar = findViewById(R.id.loading);


        Member member = getIntent().getParcelableExtra(EXTRA_MEMBER);
        if (member != null) {
            name = member.getmName();
            username = member.getmUsername();
            password = member.getmPassword();
            noPhone = member.getmNoPhone();
        }
        myCalendar = Calendar.getInstance();
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH);
        mDay = myCalendar.get(Calendar.DAY_OF_WEEK);

        edtDateOfBirth.setOnClickListener(v -> new DatePickerDialog(RegisterTwoAct.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTanggal = "dd/MM/yyyy";
                sdf = new SimpleDateFormat(formatTanggal, Locale.getDefault());
                edtDateOfBirth.setText(sdf.format(myCalendar.getTime()));
            }
        }, mYear, mMonth, mDay).show());

        btnAddPhoto.setOnClickListener(v -> showCameraPreview());

        btnRegisterTwoNext = findViewById(R.id.btn_register_two_next);
        btnRegisterTwoNext.setOnClickListener(v -> {
            if(edtEmail.getText().toString().isEmpty()) {
                edtEmail.setError(getString(R.string.not_null));
            } else if (edtAddress.getText().toString().isEmpty()) {
                edtAddress.setError(getString(R.string.not_null));
            } else if (edtDateOfBirth.getText().toString().isEmpty()) {
                edtDateOfBirth.setError(getString(R.string.not_null));
            } else {
                email = edtEmail.getText().toString();
                address = edtAddress.getText().toString();
                dateOfBirth = edtDateOfBirth.getText().toString();
                saveMember();

                SharedPreferences sf = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.putString(username_key, username);
                editor.apply();

                Intent gotoSuccessRegister = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                startActivity(gotoSuccessRegister);

            }
        });
    }

    public void saveMember() {
        progressBar.setVisibility(View.VISIBLE);
        Service = Api.getApi().create(ApiInterface.class);
        uploadImage();
        Call = Service.insertMember(name, noPhone, username, password, email, address, dateOfBirth, "/koperasimasjid/img/" + fileName);
        Call.enqueue(new Callback<ResultMember>() {
            @Override
            public void onResponse(retrofit2.Call<ResultMember> call, Response<ResultMember> response) {

                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(RegisterTwoAct.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterTwoAct.this, message, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(retrofit2.Call<ResultMember> call, Throwable t) {
                t.printStackTrace();
                Log.e("error", String.valueOf(t));
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RegisterTwoAct.this, "Jaringan Error !", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Begin_include(onRequestPermissionResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
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
            Snackbar.make(mLayout, R.string.camera_access_required, Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //request permission
                    ActivityCompat.requestPermissions(RegisterTwoAct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CAMERA);
        }
    }

    public void loadImagefromGallery() {
        //create intent to open image application like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Start intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    //when image is selected from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //when image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            //get image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                //Move to first
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

                //set image in imageView
                ivRegisterPhoto.setImageBitmap(BitmapFactory.decodeFile(imgPath));

                //get the image's filename
                String[] fileNameSegments = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                //put file name in async http post param which will used in php web app
                params.put("filename", fileName);
            } else {
                Toast.makeText(this, "You Haven't picked image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    //when upload button is clicked
    public void uploadImage() {
        //when image is selected from gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            //convert image to string using base64
            encodeImagetoString();
            //when image is not selected from gallery
        } else {
            Log.e("Error", "You must select image from gallery before you try to upload");
        }
    }

    //AsyncTask - To conver Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the image to reduce image size to make upload easey
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();

                //encode image to string
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                //put converted image string into async http post params
                params.put("image", encodedString);
                //trigger image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload(){
        makeHTTPCall();
    }

    //make http call to upload image to php server
    public void makeHTTPCall() {
        AsyncHttpClient client = new AsyncHttpClient();
        // don't forget to change ip address
        client.post("http://192.168.42.117/koperasimasjid/uploadPhotoProfile.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //hide progress dialog
                progressBar.setVisibility(View.INVISIBLE);
                //when http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Request resource not found", Toast.LENGTH_LONG).show();
                }

                //when Http response code is '500
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server", Toast.LENGTH_LONG).show();
                }

                //when http response code other than 404, 400
                else {
                    Toast.makeText(getApplicationContext(), "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dissmiss the progress bar when appliaction is closed
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}
