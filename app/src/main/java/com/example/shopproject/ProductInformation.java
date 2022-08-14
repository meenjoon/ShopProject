package com.example.shopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProductInformation {

    private String Product_name;
    private  String product_image;
    private  String product_content;
    private  String Product_category;

    private  String product_price;

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public String getProduct_category() {
        return Product_category;
    }

    public void setProduct_category(String product_category) {
        Product_category = product_category;
    }



    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}