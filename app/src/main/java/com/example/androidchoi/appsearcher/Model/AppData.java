package com.example.androidchoi.appsearcher.Model;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppData {

    private String mName;
    private Drawable mImage;
    private String mPackageName;
    private String mActivityName;

    public AppData(ResolveInfo ri, PackageManager pm) {
        mName = ri.loadLabel(pm).toString();
        mImage = ri.loadIcon(pm);
        mPackageName = ri.activityInfo.packageName;
        mActivityName = ri.activityInfo.name;
    }
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
    public String getPackageName() {
        return mPackageName;
    }

    public String getActivityName() {
        return mActivityName;
    }

}
