package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Model.PostData;
import com.example.androidchoi.appsearcher.R;

import java.util.Calendar;

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
        long date = Long.parseLong(postData.getWriteDate(), 10);
        mTextWriteDate.setText(getTime(date));
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static String getTime(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "방금 전";
        }
        else if (diff < HOUR_MILLIS) {
            return diff / MINUTE_MILLIS + "분 전";
        }
        else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "시간 전";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if(calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)){
            return String.format("%02d", calendar.get(Calendar.MONTH)+1 ) + "." + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + " "
                    + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        }else {

            return calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1) + "." + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        }
    }
}
