package com.example.daomy.foodguide.model;

/**
 * Created by PDNghiaDev on 5/10/2015.
 */
public class Calendar {
    private int idCategory;
    private String name;
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minute;
    private int endTime;

    public Calendar(int id, String name, int day, int month, int year, int hours, int minute, int endTime) {
        this.idCategory = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hours = hours;
        this.minute = minute;
        this.endTime = endTime;
    }

    public int getId() {
        return idCategory;
    }

    public void setId(int id) {
        this.idCategory = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
