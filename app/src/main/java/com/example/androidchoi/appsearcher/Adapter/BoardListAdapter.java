package com.example.androidchoi.appsearcher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Model.PostData;
import com.example.androidchoi.appsearcher.R;
import com.example.androidchoi.appsearcher.ViewHolder.BoardItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardItemViewHolder> {

    List<PostData> mItems = new ArrayList<>();

    BoardItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(BoardItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // PostData get 메소드
    public PostData getItem(int position){
        return mItems.get(position);
    }

    // Post item 추가 메소드
    public void setItems(List<PostData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public BoardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post_item, null);
        return new BoardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
