package com.example.shopproject;

/* 파이어베이스 사용을 위한 사용자 계정 정보 모델 클래스 */

public class UserAccount {
    private String idToken; // Firebase Uid(고유 토큰정보=키 값)

    private String emailId; // 이메일 아이디
    private  String password; //비밀번호
    private  String name; // 이름
    private  String date;
    private  String question;
    private  String spinner;
    private  String phone;
    private  String product;


    public  UserAccount() { // 공식 ! 파이어 베이스를 만들기 위해선 빈 생성자를 가장 먼저 호출해야함
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpinner() {
        return spinner;
    }
    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}