package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-06-14.
 */
public class PostData {

    @SerializedName("app_name")
    private String appName;
    @SerializedName("app_image")
    private String appImageURL;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("app_evaluation")
    private String evaluation;
    @SerializedName("write_date")
    private long writeDate;

    public PostData(String appName, String appImageURL, String userName, String evaluation, long writeDate) {
        this.appName = appName;
        this.appImageURL = appImageURL;
        this.userName = userName;
        this.evaluation = evaluation;
        this.writeDate = writeDate;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppImageURL() {
        return appImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public long getWriteDate() {
        return writeDate;
    }
}
