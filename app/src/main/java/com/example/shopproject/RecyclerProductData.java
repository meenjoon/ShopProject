package com.example.shopproject;

public class RecyclerProductData {
    private int product_count;
    private int product_image;
    private String product_name;
    private  String product_content;
    private String product_chatting;
    private  String product_buy;

    public RecyclerProductData(int product_count, int product_image, String product_name, String product_content, String product_chatting, String product_buy) {
        this.product_count = product_count;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_content = product_content;
        this.product_chatting = product_chatting;
        this.product_buy = product_buy;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count( int product_count) {
        this.product_count = product_count;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public String getProduct_chatting() {
        return product_chatting;
    }

    public void setProduct_chatting(String product_chatting) {
        this.product_chatting = product_chatting;
    }

    public String getProduct_buy() {
        return product_buy;
    }

    public void setProduct_buy(String product_buy) {
        this.product_buy = product_buy;
    }
}
