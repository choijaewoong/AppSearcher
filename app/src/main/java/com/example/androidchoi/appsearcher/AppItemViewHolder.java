package com.example.androidchoi.appsearcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Model.AppData;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class AppItemViewHolder extends RecyclerView.ViewHolder {

    TextView textAppName;
    ImageView imageAppImage;

    public AppItemViewHolder(View itemView) {
        super(itemView);
        textAppName = (TextView)itemView.findViewById(R.id.text_app_name);
        imageAppImage = (ImageView)itemView.findViewById(R.id.image_app_logo);
    }

    public void setItems(AppData appData) {
        textAppName.setText(appData.getName());
        imageAppImage.setImageDrawable(appData.getImage());
    }
}
