package com.example.androidchoi.appsearcher.Model;

/**
 * Created by Choi on 2016-06-14.
 */
public class PostData {

    String appName;
    String appImageURL;
    String userName;
    String evaluation;
    String writeDate;

    public PostData(String appName, String appImageURL, String userName, String evaluation, String writeDate) {
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

    public String getWriteDate() {
        return writeDate;
    }
}
