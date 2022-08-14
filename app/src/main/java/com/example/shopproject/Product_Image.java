package com.example.shopproject;

import android.graphics.Bitmap;

public class Product_Image {

    private Bitmap product_image;

    public Product_Image(Bitmap product_image) {
        this.product_image = product_image;
    }

    public Bitmap getProduct_image() {
        return product_image;
    }

    public void setProduct_image(Bitmap product_image) {
        this.product_image = product_image;
    }
}
