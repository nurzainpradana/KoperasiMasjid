package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultUser implements Parcelable {

    String value;
    String message;
    @SerializedName("result")
    private List<User> mResultUser;

    protected ResultUser(Parcel in) {
        this.mResultUser = in.createTypedArrayList(User.CREATOR);
    }

    public ResultUser() {
    }

    public static final Creator<ResultUser> CREATOR = new Creator<ResultUser>() {
        @Override
        public ResultUser createFromParcel(Parcel in) {
            return new ResultUser(in);
        }

        @Override
        public ResultUser[] newArray(int size) {
            return new ResultUser[size];
        }
    };

    public List<User> getmResultUser() {
        return mResultUser;
    }

    public void setmResultUser(List<User> mResultUser) {
        this.mResultUser = mResultUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mResultUser);
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
