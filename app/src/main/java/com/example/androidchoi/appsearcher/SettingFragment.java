package com.example.androidchoi.appsearcher;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Manager.PropertyManager;
import com.example.androidchoi.appsearcher.Model.UserSingletonData;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    TextView mTextUserEmail;
    Button mButtonSignout;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setHasOptionsMenu(true);

        mTextUserEmail = (TextView)view.findViewById(R.id.text_setting_user_email);
        mTextUserEmail.setText(UserSingletonData.getInstance().getEmail());

        mButtonSignout = (Button)view.findViewById(R.id.button_sign_out);
        mButtonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        return view;
    }

    public void signout(){
        NetworkManager.getInstance().signout(new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                    PropertyManager.getInstance().setEmail(null);
                    PropertyManager.getInstance().setPassword(null);
                    startActivity(new Intent(getActivity(), SignInActivity.class));
                    getActivity().finish();
            }

            @Override
            public void onFail(String error) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }


}
