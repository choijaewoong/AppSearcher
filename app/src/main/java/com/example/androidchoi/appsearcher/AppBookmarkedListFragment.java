package com.example.androidchoi.appsearcher;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Adapter.AppBookmarkedListAdapter;
import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.AppList;
import com.example.androidchoi.appsearcher.ViewHolder.AppBookmarkedItemViewHolder;


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

        // RecyclerVIew 세팅
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appBookmarkedList);
        mAppBookmarkedListAdapter = new AppBookmarkedListAdapter();
        mRecyclerView.setAdapter(mAppBookmarkedListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAppBookmarkedListAdapter.setOnItemClickListener(new AppBookmarkedItemViewHolder.OnItemClickListener() {
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
        NetworkManager.getInstance().showAppList(new NetworkManager.OnResultListener<AppList>() {
            @Override
            public void onSuccess(AppList result) {
                mAppBookmarkedListAdapter.setItems(result.getAppList());
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "목록을 가져오지 못하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshList(){
        getBookmarkedList();
    }


}
