package com.nurzainpradana.koperasimasjid.model;

public class CartItem {

    private String id_products;
    private String name;
    private String description;
    private String image;
    private String price;
    private String unit;

    public CartItem(String id_products, String name, String description, String image, String price, String unit) {
        this.id_products = id_products;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.unit = unit;
    }

    public String getId_products() {
        return id_products;
    }

    public void setId_products(String id_products) {
        this.id_products = id_products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
