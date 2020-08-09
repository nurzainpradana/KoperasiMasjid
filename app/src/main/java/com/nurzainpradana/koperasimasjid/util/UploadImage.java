package com.nurzainpradana.koperasimasjid.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.nurzainpradana.koperasimasjid.api.Api;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.model.ResultUser;

import java.io.ByteArrayOutputStream;

import retrofit2.Callback;
import retrofit2.Response;

public class UploadImage {
    ApiInterface Service;
    retrofit2.Call<ResultUser> Call;

    String encodedString;
    String fileName;
    String imgPath;
    String urlPhoto;

    public UploadImage(String imgPath, String fileName) {
        this.imgPath = imgPath;
        this.fileName = fileName;
    }

    public UploadImage(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }



    //when upload button is clicked
    public void uploadImage() {
        //when image is selected from gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            //convert image to string using base64
            encodeImagetoString(imgPath);
            //when image is not selected from gallery
        } else {
            Log.e("Error", "You must select image from gallery before you try to upload");
        }
    }

    //when upload button is clicked
    public void removeImage() {
        //when image is selected from gallery
        if (urlPhoto != null) {
            makeHTTPCallRemove();
        } else {
            Log.e("Error", "You must select image from gallery before you try to upload");
        }
    }

    //AsyncTask - To conver Image to String
    @SuppressLint("StaticFieldLeak")
    public void encodeImagetoString(String imgPath) {
        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
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
                //trigger image upload
                makeHTTPCallUpload();
            }
        }.execute(null, null, null);
    }

    //make http call to upload image to php server
    public void makeHTTPCallUpload() {
        Service = Api.getApi().create(ApiInterface.class);
        uploadImage();
        Call = Service.uploadPhotoProfile(encodedString, fileName);
        Call.enqueue(new Callback<ResultUser>() {
            @Override
            public void onResponse(retrofit2.Call<ResultUser> call, Response<ResultUser> response) {
                Log.d("UPLOAD", response.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<ResultUser> call, Throwable t) {
                Log.d("UPLOAD GAGAL", t.toString());
            }
        });
    }

    //make http call to upload image to php server
    public void makeHTTPCallRemove() {
        Service = Api.getApi().create(ApiInterface.class);
        uploadImage();
        Call = Service.removePhotoProfile(urlPhoto);
        Call.enqueue(new Callback<ResultUser>() {
            @Override
            public void onResponse(retrofit2.Call<ResultUser> call, Response<ResultUser> response) {
                Log.d("REMOVE", response.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<ResultUser> call, Throwable t) {
                Log.d("REMOVE GAGAL", t.toString());
            }
        });
    }
}
