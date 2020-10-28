package com.nurzainpradana.koperasimasjid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart implements Parcelable {

    @SerializedName("id_cart")
    @Expose
    private String idCart;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_products")
    @Expose
    private String idProducts;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdProducts() {
        return idProducts;
    }

    public void setIdProducts(String idProducts) {
        this.idProducts = idProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCart);
        dest.writeString(idUser);
        dest.writeString(idProducts);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeString(image);
        dest.writeString(quantity);
        dest.writeString(subtotal);
    }

    public Cart(Parcel in) {
        idCart = in.readString();
        idUser = in.readString();
        idProducts = in.readString();
        name = in.readString();
        price = in.readString();
        status = in.readString();
        image = in.readString();
        quantity = in.readString();
        subtotal = in.readString();
    }
}
