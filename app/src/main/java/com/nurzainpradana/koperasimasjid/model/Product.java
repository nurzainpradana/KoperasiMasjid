package com.nurzainpradana.koperasimasjid.model;



public class Product {

    private String id_products;
    private String name;
    private String price;
    private double unit;
    private String description;
    private String image;
    private String favorite;

    public Product(String id_products, String name, String price, double unit, String description, String image, String favorite) {
        this.id_products = id_products;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.image = image;
        this.favorite = favorite;
    }

    public String getId_products() {
        return id_products;
    }

    public void setId_products(String id_products) {
        this.id_products = id_products;
    }

    public void setId_produts(int id_produts) {
        this.id_products = id_products;
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

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
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

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
