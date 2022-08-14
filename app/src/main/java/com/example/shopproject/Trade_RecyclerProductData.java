package com.example.shopproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Trade_RecyclerProductData extends RecyclerView.Adapter {
    private String product_count;
    private String product_image;

    private int product_heart_black_image;
    private int product_heart_red_image;

    private String product_heart_count;

    private String product_name;
    private String product_price;
    private String product_city;

    private String product_content;
    private String product_category;
    private String product_image_url;

    private ArrayList product_image_urls;

    private String product_imageurls;

    private String click_count;

    private String product_location;

    public Trade_RecyclerProductData(String product_count, String product_image, int product_heart_black_image, int product_heart_red_image, String product_heart_count, String product_name, String product_price, String product_city, String product_content, String product_category, String product_image_url, ArrayList product_image_urls, String product_imageurls, String click_count, String product_location) {
        this.product_count = product_count;
        this.product_image = product_image;
        this.product_heart_black_image = product_heart_black_image;
        this.product_heart_red_image = product_heart_red_image;
        this.product_heart_count = product_heart_count;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_city = product_city;
        this.product_content = product_content;
        this.product_category = product_category;
        this.product_image_url = product_image_url;
        this.product_image_urls = product_image_urls;
        this.product_imageurls = product_imageurls;
        this.click_count = click_count;
        this.product_location = product_location;
    }

    public int getProduct_heart_red_image() {
        return product_heart_red_image;
    }

    public void setProduct_heart_red_image(int product_heart_red_image) {
        this.product_heart_red_image = product_heart_red_image;
    }

    public String getProduct_location() {
        return product_location;
    }

    public void setProduct_location(String product_location) {
        this.product_location = product_location;
    }

    public String getClick_count() {
        return click_count;
    }

    public void setClick_count(String click_count) {
        this.click_count = click_count;
    }

    public String getProduct_imageurls() {
        return product_imageurls;
    }

    public void setProduct_imageurls(String product_imageurls) {
        this.product_imageurls = product_imageurls;
    }

    public ArrayList getProduct_image_urls() {
        return product_image_urls;
    }

    public void setProduct_image_urls(ArrayList product_image_urls) {
        this.product_image_urls = product_image_urls;
    }





    public String getProduct_content() {

        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_heart_black_image() {
        return product_heart_black_image;
    }

    public void setProduct_heart_black_image(int product_heart_black_image) {
        this.product_heart_black_image = product_heart_black_image;
    }

    public String getProduct_heart_count() {
        return product_heart_count;
    }

    public void setProduct_heart_count(String product_heart_count) {
        this.product_heart_count = product_heart_count;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_city() {
        return product_city;
    }

    public void setProduct_city(String product_city) {
        this.product_city = product_city;
    }
}


