package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Model.AppServerData;
import com.example.androidchoi.appsearcher.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class AppRecommendItemViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String packageName, String activityName, int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView textAppName;
    ImageView imageAppImage;
    String packageName;
    String activityName;

    public AppRecommendItemViewHolder(View itemView) {
        super(itemView);
        textAppName = (TextView)itemView.findViewById(R.id.text_app_name);
        imageAppImage = (ImageView)itemView.findViewById(R.id.image_app_logo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(packageName, activityName, position);
                }
            }
        });
    }

    public void setItems(AppServerData serverData){

    }
}
