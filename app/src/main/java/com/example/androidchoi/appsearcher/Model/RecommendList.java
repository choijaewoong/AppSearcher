package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-06-14.
 */
public class RecommendList {

    private int count;
    @SerializedName("recommends")
    private List<RecommendData> mRecommendDataList = new ArrayList<>();

    public List<RecommendData> getRecommendDataList() {
        return mRecommendDataList;
    }
    public int getCount() {
        return count;
    }
}
