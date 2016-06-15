package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-06-14.
 */
public class RecommendData {

    @SerializedName("name")
    private String appName;
    @SerializedName("description")
    private String appDescription;
    @SerializedName("image")
    private String appImageURL;
    @SerializedName("download_url")
    private String appDownloadURL;

    public RecommendData(String appName, String appDescription, String appImageURL, String appDownloadURL) {
        this.appName = appName;
        this.appDescription = appDescription;
        this.appImageURL = appImageURL;
        this.appDownloadURL = appDownloadURL;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public String getAppImageURL() {
        return appImageURL;
    }

    public String getAppDownloadURL() {
        return appDownloadURL;
    }
}
