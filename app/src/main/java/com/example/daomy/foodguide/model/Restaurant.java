package com.example.daomy.foodguide.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by PDNghiaDev on 4/14/2015.
 */
public class Restaurant implements Parcelable {
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

    protected Restaurant(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mImage = in.readString();
        mAddress = in.readString();
        mPrice = in.readString();
        mPhone = in.readString();
        mDistrict = in.readString();
        mLocation = in.readString();
        mTime = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
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

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmDistrict() {
        return mDistrict;
    }

    public void setmDistrict(String mDistrict) {
        this.mDistrict = mDistrict;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
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
        dest.writeString(mAddress);
        dest.writeString(mPrice);
        dest.writeString(mPhone);
        dest.writeString(mDistrict);
        dest.writeString(mLocation);
        dest.writeString(mTime);
    }
}
