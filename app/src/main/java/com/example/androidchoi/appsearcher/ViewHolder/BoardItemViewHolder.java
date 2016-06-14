package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Model.PostData;
import com.example.androidchoi.appsearcher.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class BoardItemViewHolder extends RecyclerView.ViewHolder {

    TextView mTextAppName;
    TextView mTextUserName;
    TextView mTextAppEvaluation;
    ImageView mImageAppURL;
    TextView mTextWriteDate;

    public interface OnItemClickListener {
        public void onItemClick(String packageName, String activityName, int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BoardItemViewHolder(View itemView) {
        super(itemView);
        mTextAppName = (TextView)itemView.findViewById(R.id.text_post_app_name);
        mTextAppEvaluation = (TextView)itemView.findViewById(R.id.text_post_app_evalation);
        mTextUserName = (TextView)itemView.findViewById(R.id.text_post_user_name);
        mTextWriteDate = (TextView)itemView.findViewById(R.id.text_post_date);
    }

    public void setItems(PostData postData){
        mTextAppName.setText(postData.getAppName());
        mTextUserName.setText(postData.getUserName());
        mTextAppEvaluation.setText(postData.getEvaluation());
        mTextWriteDate.setText(postData.getWriteDate());
    }
}
