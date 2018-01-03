package com.example.daomy.foodguide.model;

/**
 * Created by PDNghiaDev on 4/15/2015.
 */
public class Categories {
    private String mIDCategory;
    private String mName;
    private String mImage;

    public Categories() {
    }

    public Categories(String IDCategory, String name, String image) {
        mIDCategory = IDCategory;
        mName = name;
        mImage = image;
    }

    public String getIDCategory() {
        return mIDCategory;
    }

    public void setIDCategory(String IDCategory) {
        mIDCategory = IDCategory;
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
}
