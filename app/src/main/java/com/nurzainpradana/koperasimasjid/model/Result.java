package com.nurzainpradana.koperasimasjid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param message
     * @param value
     */
    public Result(Integer value, String message) {
        super();
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
