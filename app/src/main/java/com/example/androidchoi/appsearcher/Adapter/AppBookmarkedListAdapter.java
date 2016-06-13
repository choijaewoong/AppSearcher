package com.example.androidchoi.appsearcher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Model.AppServerData;
import com.example.androidchoi.appsearcher.R;
import com.example.androidchoi.appsearcher.ViewHolder.AppBookmarkedItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AppBookmarkedListAdapter extends RecyclerView.Adapter<AppBookmarkedItemViewHolder> {

    List<AppServerData> mItems = new ArrayList<>();

    AppBookmarkedItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(AppBookmarkedItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // PlaceData get 메소드
    public AppServerData getItem(int position){
        return mItems.get(position);
    }

    // PlaceItem 개별 추가 메소드 (Sample Data용)
    public void addItems(AppServerData placeServerData){
        mItems.add(placeServerData);
        notifyDataSetChanged();
    }

    // PlaceList item 추가 메소드
    public void setItems(List<AppServerData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public AppBookmarkedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_bookmarked_item, null);
        return new AppBookmarkedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppBookmarkedItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
