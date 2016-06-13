package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppServerDataLab {

    @SerializedName("app")
    AppServerData mAppServerData;

    public AppServerData getAppServerData() {
        return mAppServerData;
    }
}
