package com.nurzainpradana.koperasimasjid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddtoCart {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Information")
    @Expose
    private Information information;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public class Information {

        @SerializedName("qoute_id")
        @Expose
        private String qouteId;
        @SerializedName("cart_count")
        @Expose
        private Integer cartCount;

        public String getQouteId() {
            return qouteId;
        }

        public void setQouteId(String qouteId) {
            this.qouteId = qouteId;
        }

        public Integer getCartCount() {
            return cartCount;
        }

        public void setCartCount(Integer cartCount) {
            this.cartCount = cartCount;
        }
    }



}
