package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by dongja94 on 2016-01-28.
 */
public class CustomPopupWindow extends PopupWindow {

    LinearLayout mLayoutAppBookmark;
    LinearLayout mLayoutAppSetting;

    String mAppPackageName;
    String mAppActivityName;

    public void setAppInfo(String packageName, String activityName){
        mAppPackageName = packageName;
        mAppActivityName = activityName;
    }

    public CustomPopupWindow(Context context) {
        super(context);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setOutsideTouchable(true);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_custom, null);
        mLayoutAppBookmark = (LinearLayout)view.findViewById(R.id.layout_app_bookmark);
        mLayoutAppBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mLayoutAppSetting = (LinearLayout)view.findViewById(R.id.layout_app_setting);
        mLayoutAppSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mAppPackageName, null);
                intent.setData(uri);
                getContentView().getContext().startActivity(intent);
                dismiss();
            }
        });
        setContentView(view);
    }
}
