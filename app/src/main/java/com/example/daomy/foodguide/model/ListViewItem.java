package com.example.daomy.foodguide.model;

/**
 * Created by PDNghiaDev on 5/5/2015.
 */
public class ListViewItem {

    public String lvImageID;
    public String lvTextName;
    public String lvTextDescription;
    public String lvTextPrice;
    int id;

    public ListViewItem(int id, String lvImageID, String lvTextName, String lvTextDescription, String lvTextPrice) {
        this.id = id;
        this.lvImageID = lvImageID;
        this.lvTextName = lvTextName;
        this.lvTextDescription = lvTextDescription;
        this.lvTextPrice = lvTextPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLvTextPrice() {
        return lvTextPrice;
    }

    public void setLvTextPrice(String lvTextPrice) {
        this.lvTextPrice = lvTextPrice;
    }

    public String getLvImageID() {
        return lvImageID;
    }

    public void setLvImageID(String lvImageID) {
        this.lvImageID = lvImageID;
    }

    public String getLvTextName() {
        return lvTextName;
    }

    public void setLvTextName(String lvTextName) {
        this.lvTextName = lvTextName;
    }

    public String getLvTextDescription() {
        return lvTextDescription;
    }

    public void setLvTextDescription(String lvTextDescription) {
        this.lvTextDescription = lvTextDescription;
    }
}
