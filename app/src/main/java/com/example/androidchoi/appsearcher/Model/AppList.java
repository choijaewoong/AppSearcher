package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppList {

    int count;
    @SerializedName("apps")
    List<AppServerData> mAppList = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public List<AppServerData> getAppList() {
        return mAppList;
    }
}
