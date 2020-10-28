package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("total_order")
    @Expose
    private String totalOrder;
    @SerializedName("status")
    @Expose
    private String status;

    protected Transaction(Parcel in) {
        idTransaction = in.readString();
        dateOrder = in.readString();
        idUser = in.readString();
        totalOrder = in.readString();
        status = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTransaction);
        dest.writeString(dateOrder);
        dest.writeString(idUser);
        dest.writeString(totalOrder);
        dest.writeString(status);
    }
}

