package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result implements Parcelable {

    String value;
    String message;
    @SerializedName("result")
    private List<Member> mResultMember;

    protected Result(Parcel in) {
        this.mResultMember = in.createTypedArrayList(Member.CREATOR);
    }

    public Result() {
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public List<Member> getmResultMember() {
        return mResultMember;
    }

    public void setmResultMember (List<Member> mResultMember1) {
        this.mResultMember = mResultMember1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mResultMember);
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
