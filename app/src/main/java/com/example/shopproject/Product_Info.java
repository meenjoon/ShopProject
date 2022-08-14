package com.example.shopproject;/* 파이어베이스 사용을 위한 사용자 계정 정보 모델 클래스 */

public class Product_Info {


    private String image_url; // 이메일 아이디

    private  String product_title; // 글 제목

    private  String spinner ; // 질문

    private  String price; // 가격

    private  String product_content; // 내용

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }
}