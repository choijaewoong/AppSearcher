package com.example.androidchoi.appsearcher;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppListCursorAdapter extends RecyclerViewCursorAdapter<AppItemViewHolder> {

    AppItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(AppItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public AppListCursorAdapter(Cursor cursor){
        super(cursor);
    }

    @Override
    public AppItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_item, null);
        return new AppItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppItemViewHolder holder, final Cursor cursor) {
        holder.bindData(cursor);
        holder.setOnItemClickListener(mItemClickListener);
    }
}
