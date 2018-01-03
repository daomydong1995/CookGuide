package com.example.daomy.foodguide.activity;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParsePush;
import com.parse.PushService;

import com.example.daomy.foodguide.activity.MainActivity;

/**
 * Created by PDNghiaDev on 5/25/2015.
 */
public class FoodGuide extends Application {

    public FoodGuide() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse SDK.
        Parse.initialize(this, "lsAr38674sNlRrkEC0D2Y4d4J1oSGCWqTGyBBuJS", "zWjaJ5b0uLZcQ7YDZBrNeoIBOahUDi5WIVxhOJyg");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, MainActivity.class);
    }
}
