package com.example.daomy.foodguide.model;

import java.io.Serializable;

/**
 * Created by PDNghiaDev on 4/14/2015.
 */
public class Restaurant implements Serializable {
    private int mId;
    private String mName;
    private String mImage;
    private String mAddress;
    private String mPrice;
    private String mPhone;
    private String mDistrict;
    private String mLocation;
    private String mTime;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String image, String address,
                      String price, String phone, String district,
                      String location, String time) {
        mId = id;
        mName = name;
        mImage = image;
        mAddress = address;
        mPrice = price;
        mPhone = phone;
        mDistrict = district;
        mLocation = location;
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
