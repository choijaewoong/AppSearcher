package com.example.androidchoi.appsearcher;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Adapter.AppRecommendListAdapter;
import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.RecommendData;
import com.example.androidchoi.appsearcher.Model.RecommendList;
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

        mAppRecommendListAdapter.addItems(new RecommendData("추천asdfasdfasdfasd!", "밍ㅇ민ㅇ라민알\n\n\nasdfasdfasdf", "", ""));

        mAppRecommendListAdapter.setOnItemClickListener(new AppRecommendItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String downloadURL, int position) {
                try {
                    Uri uriUrl = Uri.parse(downloadURL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "페이지를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e){
                    Toast.makeText(getContext(), "페이지를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getRecommendList();

        return view;
    }

    // 추천 앱을 서버에서 불러오는 메소드
    public void getRecommendList(){
        NetworkManager.getInstance().showRecommendList(new NetworkManager.OnResultListener<RecommendList>() {
            @Override
            public void onSuccess(RecommendList result) {
                mAppRecommendListAdapter.setItems(result.getRecommendDataList());
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "목록을 가져오지 못하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
