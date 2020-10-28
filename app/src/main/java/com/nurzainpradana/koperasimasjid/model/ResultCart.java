package com.nurzainpradana.koperasimasjid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultCart {

    @SerializedName("result")
    @Expose
    private List<Cart> result = null;
    @SerializedName("total")
    @Expose
    private String total;

    public List<Cart> getResult() {
        return result;
    }

    public void setResult(List<Cart> result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}