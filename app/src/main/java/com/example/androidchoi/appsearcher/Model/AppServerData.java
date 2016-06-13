package com.example.androidchoi.appsearcher.Model;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppServerData {

    private String mName;
    private byte[] mImageInByte;
    private String mPackageName;
    private String mActivityName;

    public AppServerData(ResolveInfo ri, PackageManager pm) {
        mName = ri.loadLabel(pm).toString();
        Drawable drawable = ri.loadIcon(pm);
        BitmapDrawable bitmapDrawable = ((BitmapDrawable)drawable);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        mImageInByte = stream.toByteArray();
        mPackageName = ri.activityInfo.packageName;
        mActivityName = ri.activityInfo.name;
    }
//    public AppData(String name, Drawable image) {
//        mName = name;
//        mImage = image;
//    }
    public String getName() {
        return mName;
    }
    public byte[] getImage() {
        return mImageInByte;
    }
    public String getPackageName() { return mPackageName; }
    public String getActivityName() {
        return mActivityName;
    }

}
