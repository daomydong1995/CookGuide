package com.example.daomy.foodguide.model;

import java.io.Serializable;

/**
 * Created by PDNghiaDev on 4/14/2015.
 */
public class Recipes implements Serializable {
    private int mId;
    private String mName;
    private String mImage;
    private int mTime;
    private int mServing;
    private int mKcal;
    private String mIngredients;
    private String mInstruction;

    public Recipes() {
    }

    public Recipes(int id, String name, String image, int time, int serving, int kcal, String ingredients, String instruction) {
        mId = id;
        mName = name;
        mImage = image;
        mTime = time;
        mServing = serving;
        mKcal = kcal;
        mIngredients = ingredients;
        mInstruction = instruction;
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

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public int getServing() {
        return mServing;
    }

    public void setServing(int serving) {
        mServing = serving;
    }

    public int getKcal() {
        return mKcal;
    }

    public void setKcal(int kcal) {
        mKcal = kcal;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public String getInstruction() {
        return mInstruction;
    }

    public void setInstruction(String instruction) {
        mInstruction = instruction;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
