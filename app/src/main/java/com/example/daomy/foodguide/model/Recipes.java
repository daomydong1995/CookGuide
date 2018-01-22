package com.example.daomy.foodguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Recipes implements Parcelable {
    private int mId;
    private String mName;
    private String mImage;
    private int mTime;
    private int mServing;
    private int mKcal;
    private String mIngredients;
    private String mInstruction;
    private Categories categories;
    private String tagName;
    private String mCodeVideo;
    public Recipes() {
    }

    public Recipes(int mId, String mName, String mImage, int mTime, int mServing, int mKcal, String mIngredients, String mInstruction, Categories categories, String tagName, String mCodeVideo) {
        this.mId = mId;
        this.mName = mName;
        this.mImage = mImage;
        this.mTime = mTime;
        this.mServing = mServing;
        this.mKcal = mKcal;
        this.mIngredients = mIngredients;
        this.mInstruction = mInstruction;
        this.categories = categories;
        this.tagName = tagName;
        this.mCodeVideo = mCodeVideo;
    }

    protected Recipes(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mImage = in.readString();
        mTime = in.readInt();
        mServing = in.readInt();
        mKcal = in.readInt();
        mIngredients = in.readString();
        mInstruction = in.readString();
        tagName = in.readString();
        mCodeVideo = in.readString();
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public int getmTime() {
        return mTime;
    }

    public void setmTime(int mTime) {
        this.mTime = mTime;
    }

    public int getmServing() {
        return mServing;
    }

    public void setmServing(int mServing) {
        this.mServing = mServing;
    }

    public int getmKcal() {
        return mKcal;
    }

    public void setmKcal(int mKcal) {
        this.mKcal = mKcal;
    }

    public String getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(String mIngredients) {
        this.mIngredients = mIngredients;
    }

    public String getmInstruction() {
        return mInstruction;
    }

    public void setmInstruction(String mInstruction) {
        this.mInstruction = mInstruction;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getmCodeVideo() {
        return mCodeVideo;
    }

    public void setmCodeVideo(String mCodeVideo) {
        this.mCodeVideo = mCodeVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mImage);
        dest.writeInt(mTime);
        dest.writeInt(mServing);
        dest.writeInt(mKcal);
        dest.writeString(mIngredients);
        dest.writeString(mInstruction);
        dest.writeString(tagName);
        dest.writeString(mCodeVideo);
    }
}
