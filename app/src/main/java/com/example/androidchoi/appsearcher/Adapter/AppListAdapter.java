package com.example.androidchoi.appsearcher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.AppItemViewHolder;
import com.example.androidchoi.appsearcher.Model.AppData;
import com.example.androidchoi.appsearcher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-03-11.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppItemViewHolder> {

    List<AppData> mItems = new ArrayList<>();

    AppItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(AppItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // 어댑터에 AppList item 추가하는 메소드
    public void setItems(List<AppData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public AppItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_item, null);
        return new AppItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
