package com.example.androidchoi.appsearcher.Adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.R;
import com.example.androidchoi.appsearcher.ViewHolder.AppDefualtItemViewHolder;
import com.example.androidchoi.appsearcher.ViewHolder.AppItemViewHolder;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppDefaultListCursorAdapter extends AppListCursorAdapter {

    AppDefualtItemViewHolder.OnPopUpButtonClickListener mPopUpButtonClickListener;
    public void setOnPopUpButtonClickListener(AppDefualtItemViewHolder.OnPopUpButtonClickListener listener){
        mPopUpButtonClickListener = listener;
    }

    public AppDefaultListCursorAdapter(Cursor cursor){
        super(cursor);
    }

    @Override
    public AppItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_default_item, null);
        return new AppDefualtItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppItemViewHolder holder, final Cursor cursor) {
        super.onBindViewHolder(holder,cursor);
        ((AppDefualtItemViewHolder)holder).setOnPopUpButtonClickListener(mPopUpButtonClickListener);
    }




}
