package com.example.androidchoi.appsearcher.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-06-14.
 */
public class PostList {

    private int count;
    @SerializedName("posts")
    private List<PostData> mPostList = new ArrayList<>();

    public List<PostData> getPostList() {
        return mPostList;
    }
    public int getCount() {

        return count;
    }
}
