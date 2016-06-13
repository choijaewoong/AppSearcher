package com.example.androidchoi.appsearcher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Model.AppServerData;
import com.example.androidchoi.appsearcher.R;
import com.example.androidchoi.appsearcher.ViewHolder.AppRecommendItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AppRecommendListAdapter extends RecyclerView.Adapter<AppRecommendItemViewHolder> {

    List<AppServerData> mItems = new ArrayList<>();

    AppRecommendItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(AppRecommendItemViewHolder.OnItemClickListener listener) {
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
    public AppRecommendItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_bookmarked_item, null);
        return new AppRecommendItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppRecommendItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
