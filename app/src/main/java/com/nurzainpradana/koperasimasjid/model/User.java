package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User implements Parcelable {

    @SerializedName("id_user")
    private int mIdUser;

    @SerializedName("name")
    private String mName;

    @SerializedName("no_phone")
    private String mNoPhone;

    @SerializedName("username")
    public String mUsername;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("date_of_birth")
    private Date mDateOfBirth;

    @SerializedName("photo_profile")
    private String mPhotoProfile;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdUser);
        dest.writeString(mName);
        dest.writeString(mNoPhone);
        dest.writeString(mUsername);
        dest.writeString(mPassword);
        dest.writeString(mEmail);
        dest.writeString(mAddress);
        dest.writeString(mPhotoProfile);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public User(Parcel in) {
        mIdUser = in.readInt();
        mName = in.readString();
        mNoPhone = in.readString();
        mUsername = in.readString();
        mPassword = in.readString();
        mEmail = in.readString();
        mAddress = in.readString();
        mDateOfBirth = (Date) in.readSerializable();
        mPhotoProfile = in.readString();
    }

    public int getmIdUser() {
        return mIdUser;
    }

    public void setmIdUser(int mIdMember) {
        this.mIdUser = mIdMember;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public User(){
    }

    public String getmNoPhone() {
        return mNoPhone;
    }

    public void setmNoPhone(String mNoPhone) {
        this.mNoPhone = mNoPhone;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public Date getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(Date mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getmPhotoProfile() {
        return mPhotoProfile;
    }

    public void setmPhotoProfile(String mPhotoProfile) {
        this.mPhotoProfile = mPhotoProfile;
    }
}
