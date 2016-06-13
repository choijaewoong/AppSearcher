package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.AppServerData;

/**
 * Created by dongja94 on 2016-01-28.
 */
public class CustomPopupWindow extends PopupWindow {

    LinearLayout mLayoutAppBookmark;
    LinearLayout mLayoutAppSetting;

    String mAppName;
    String mAppImageURL;
    String mAppPackageName;
    String mAppActivityName;

    public void setAppInfo(String appName, String appImageURL, String packageName, String activityName){
        mAppName = appName;
        mAppImageURL = appImageURL;
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
                addBookmarkeApp();
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

    public void addBookmarkeApp(){
        NetworkManager.getInstance().addApp(mAppName, mAppImageURL, mAppPackageName, mAppActivityName,
                new NetworkManager.OnResultListener<AppServerData>() {
                    @Override
                    public void onSuccess(AppServerData result) {
                        Toast.makeText(MyApplication.getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFail(String error) {
                        Log.i("error : ", error);
                        Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
