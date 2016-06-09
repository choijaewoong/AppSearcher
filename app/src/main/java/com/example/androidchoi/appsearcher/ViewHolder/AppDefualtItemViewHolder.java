package com.example.androidchoi.appsearcher.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.androidchoi.appsearcher.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class AppDefualtItemViewHolder extends AppItemViewHolder {

    public interface OnPopUpButtonClickListener {
        public void onPopUpButtonClick(View view, String packageName, String activityName, int position);
    }
    OnPopUpButtonClickListener mPopUpButtonClickListenerListener;
    public void setOnPopUpButtonClickListener(OnPopUpButtonClickListener listener) {
        mPopUpButtonClickListenerListener = listener;
    }

    ImageView imagePopUpButton;

    public AppDefualtItemViewHolder(View itemView) {
        super(itemView);
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
    }
}
