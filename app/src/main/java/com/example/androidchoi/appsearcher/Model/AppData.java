package com.example.androidchoi.appsearcher.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppData {

    private String mName;
    private Drawable mImage;

    public AppData(String name, Drawable image) {
        mName = name;
        mImage = image;
    }
    public String getName() {
        return mName;
    }
    public Drawable getImage() {
        return mImage;
    }
}
