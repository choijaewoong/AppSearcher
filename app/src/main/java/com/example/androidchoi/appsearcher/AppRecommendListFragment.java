package com.example.androidchoi.appsearcher;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Adapter.AppRecommendListAdapter;
import com.example.androidchoi.appsearcher.ViewHolder.AppRecommendItemViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppRecommendListFragment extends Fragment {

    RecyclerView mRecyclerView;
    AppRecommendListAdapter mAppRecommendListAdapter;

    public AppRecommendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_recommend_list, container, false);

        // RecyclerVIew 세팅
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appRecommendList);
        mAppRecommendListAdapter = new AppRecommendListAdapter();
        mRecyclerView.setAdapter(mAppRecommendListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAppRecommendListAdapter.setOnItemClickListener(new AppRecommendItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String packageName, String activityName, int position) {
                ((MainActivity) getActivity()).closeSearchView(); // 앱 클릭 시 SearchView 닫음.
                try {
                    //해당 App의 PackageName, activityName을 이용해 App 실행
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName(packageName, activityName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "존재하지 않는 Application 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getBookmarkedList();

        return view;
    }

    // 즐겨찾기로 추가된 앱을 서버에서 불러오는 메소드
    public void getBookmarkedList(){

    }


}
