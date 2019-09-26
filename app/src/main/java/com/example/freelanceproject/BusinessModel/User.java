package com.example.freelanceproject.BusinessModel;


import java.util.UUID;

public class User {

    public final static int FREELANCER_ACCOUNT = 1;
    public final static int CLIENT_ACCOUNT = 2;

    private String mId;
    private int mAccType;

    private String mName;
    private String mUserName;
    private String mEmail;
    private String mDescription;
    private String mImgPath;

    private String mPassword;

    // TODO: 9/4/2019 implement Portfolio class for storing users portfolios
    // TODO: 9/4/2019 implement Slills class for storing users skills and certificates information


    // TODO: 9/4/2019 password handling to be implemented

    private int mAge = 0;
    private int mCredScore;
    private int mRatesMin;
    private int mRatesMax;

    private Portfolio mPortfolio;

    public User(){}

    public User(String name, String userName, String email, int age){
        mName = name;
        mUserName = userName;
        mEmail = email;
        mAge = age;
    }

    public String getId() {
      return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getImgPath() {
        return mImgPath;
    }

    public void setImgPath(String imgPath) {
        mImgPath = imgPath;
    }

    public int getCredScore() {
        return mCredScore;
    }

    public void setCredScore(int credScore) {
        mCredScore = credScore;
    }

    public int getRatesMin() {
        return mRatesMin;
    }

    public void setRatesMin(int ratesMin) {
        mRatesMin = ratesMin;
    }

    public int getRatesMax() {
        return mRatesMax;
    }

    public void setRatesMax(int ratesMax) {
        mRatesMax = ratesMax;
    }

    public int getAccType() {
        return mAccType;
    }

    public void setAccType(int accType) {
        mAccType = accType;
    }

    public Portfolio getPortfolio() {
        return mPortfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        mPortfolio = portfolio;
    }

    public String getPassword() {
         return mPassword;
     }

    public void setPassword(String password) {
         mPassword = password;
     }

     public static String generateUniqieId(){
         return UUID.randomUUID().toString();
     }

    // TODO: 9/4/2019 Billing functionality to be implemented
}
