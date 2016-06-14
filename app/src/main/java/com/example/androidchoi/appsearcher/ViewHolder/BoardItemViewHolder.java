package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.androidchoi.appsearcher.Model.PostData;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class BoardItemViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String packageName, String activityName, int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BoardItemViewHolder(View itemView) {
        super(itemView);
    }

    public void setItems(PostData postData){
    }
}
