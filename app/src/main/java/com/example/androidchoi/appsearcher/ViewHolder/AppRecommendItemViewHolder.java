package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Model.RecommendData;
import com.example.androidchoi.appsearcher.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class AppRecommendItemViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String downloadURL, int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView textAppName;
    TextView textAppDescription;
    ImageView imageAppImage;
    String mAppDownloadURL;

    public AppRecommendItemViewHolder(View itemView) {
        super(itemView);
        textAppName = (TextView)itemView.findViewById(R.id.text_app_name);
        textAppDescription = (TextView)itemView.findViewById(R.id.text_app_description);
        imageAppImage = (ImageView)itemView.findViewById(R.id.image_app_logo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mAppDownloadURL, position);
                }
            }
        });
    }

    public void setItems(RecommendData recommendData){
        textAppName.setText(recommendData.getAppName());
        textAppDescription.setText(recommendData.getAppDescription().toString());
        mAppDownloadURL = recommendData.getAppDownloadURL();
    }
}
