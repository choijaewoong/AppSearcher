package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppServerData {

    @SerializedName("name")
    private String mName;
    @SerializedName("image")
    private String mImageURL;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName("activity_name")
    private String mActivityName;

    public String getName() {
        return mName;
    }
    public String getImage() {
        return mImageURL;
    }
    public String getPackageName() { return mPackageName; }
    public String getActivityName() {
        return mActivityName;
    }

}
