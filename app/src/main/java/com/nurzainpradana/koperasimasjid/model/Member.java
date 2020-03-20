package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Member implements Parcelable {

    @SerializedName("id_member")
    private int mIdMember;

    @SerializedName("name")
    private String mName;

    @SerializedName("no_phone")
    private String mNoPhone;

    @SerializedName("username")
    private String mUsername;

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
        dest.writeInt(mIdMember);
        dest.writeString(mName);
        dest.writeString(mNoPhone);
        dest.writeString(mUsername);
        dest.writeString(mPassword);
        dest.writeString(mEmail);
        dest.writeString(mAddress);
        dest.writeString(mPhotoProfile);
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public Member(Parcel in) {
        this.mIdMember = in.readInt();
        this.mName = in.readString();
        this.mNoPhone = in.readString();
        this.mUsername = in.readString();
        this.mPassword = in.readString();
        this.mEmail = in.readString();
        this.mAddress = in.readString();
        this.mDateOfBirth = (Date) in.readSerializable();
        this.mPhotoProfile = in.readString();
    }

    public Member(int mIdMember, String mName, String mNoPhone, String mUsername, String mPassword, String mEmail, String mAddress, Date mDateOfBirth, String mPhotoProfile) {
        this.mIdMember = mIdMember;
        this.mName = mName;
        this.mNoPhone = mNoPhone;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mDateOfBirth = mDateOfBirth;
        this.mPhotoProfile = mPhotoProfile;
    }

    public int getmIdMember() {
        return mIdMember;
    }

    public void setmIdMember(int mIdMember) {
        this.mIdMember = mIdMember;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Member(){
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
