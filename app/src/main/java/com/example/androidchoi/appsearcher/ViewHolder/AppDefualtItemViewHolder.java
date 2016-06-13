package com.example.androidchoi.appsearcher.ViewHolder;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.DatabaseHelper;
import com.example.androidchoi.appsearcher.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class AppDefualtItemViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String packageName, String activityName, int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnPopUpButtonClickListener {
        public void onPopUpButtonClick(View view, String packageName, String activityName, int position);
    }
    OnPopUpButtonClickListener mPopUpButtonClickListenerListener;
    public void setOnPopUpButtonClickListener(OnPopUpButtonClickListener listener) {
        mPopUpButtonClickListenerListener = listener;
    }

    TextView textAppName;
    ImageView imageAppImage;
    String packageName;
    String activityName;
    ImageView imagePopUpButton;

    public AppDefualtItemViewHolder(View itemView) {
        super(itemView);
        textAppName = (TextView)itemView.findViewById(R.id.text_app_name);
        imageAppImage = (ImageView)itemView.findViewById(R.id.image_app_logo);
        imagePopUpButton = (ImageView)itemView.findViewById(R.id.image_pop_up_button);
        imagePopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mPopUpButtonClickListenerListener != null && position != RecyclerView.NO_POSITION) {
                    mPopUpButtonClickListenerListener.onPopUpButtonClick(v, packageName, activityName, position);
                }
            }
        });
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

//    public void setItems(AppData appData) {
//        Bitmap bitmap = BitmapFactory.decodeByteArray(
//                appData.getImage(), 0,
//                appData.getImage().length);
//
//        textAppName.setText(appData.getName());
//        imageAppImage.setImageBitmap(bitmap);
////        imageAppImage.setImageDrawable(appData.getImage());
//        packageName = appData.getPackageName();
//        activityName = appData.getActivityName();
//    }
    public void bindData(final Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
        textAppName.setText(name);
        byte[] byteImage = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        imageAppImage.setImageBitmap(bitmap);
        packageName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PACKAGE_NAME));
        activityName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVITY_NAME));
    }
}
