package com.azhar.openstory.model;



public class ProductModel {
    public String name, store, img;
    public long price;
//    public int discount;
//    public float rating;

    public ProductModel(String name, String store, long price, String img) {
        this.name = name;
        this.store = store;
        this.price = price;
//        this.price_old = price_old;
        this.img = img;
//        this.rating = rating;
//        this.discount = discount;
    }
}
