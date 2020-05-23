package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultMember implements Parcelable {

    String value;
    String message;
    @SerializedName("result")
    private List<Member> mResultMember;

    protected ResultMember(Parcel in) {
        this.mResultMember = in.createTypedArrayList(Member.CREATOR);
    }

    public ResultMember() {
    }

    public static final Creator<ResultMember> CREATOR = new Creator<ResultMember>() {
        @Override
        public ResultMember createFromParcel(Parcel in) {
            return new ResultMember(in);
        }

        @Override
        public ResultMember[] newArray(int size) {
            return new ResultMember[size];
        }
    };

    public List<Member> getmResultMember() {
        return mResultMember;
    }

    public void setmResultMember (List<Member> mResultMember) {
        this.mResultMember = mResultMember;
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
