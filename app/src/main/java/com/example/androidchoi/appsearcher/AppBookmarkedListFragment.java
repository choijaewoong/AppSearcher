package com.example.androidchoi.appsearcher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Adapter.AppBookmarkedListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppBookmarkedListFragment extends Fragment {

    RecyclerView mRecyclerView;
    AppBookmarkedListAdapter mAppBookmarkedListAdapter;

    public AppBookmarkedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_bookmarked_list, container, false);



        return view;
    }


}
