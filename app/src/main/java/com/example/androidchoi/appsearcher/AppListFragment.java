package com.example.androidchoi.appsearcher;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Model.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppListFragment extends Fragment {

    RecyclerView mRecyclerView;
    AppListAdapter mAdapter;

    public AppListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_app_list, container, false);

        //암시적 인텐트
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // appList를 가져오기 위한 PackageManager 객체 생성
        final PackageManager pm = getActivity().getPackageManager();
//        List<ResolveInfo> appList = pm.queryIntentActivities(intent, 0);

        // AppData 객체 리스트
        List<AppData> appList = new ArrayList<>();
        for(ResolveInfo ri : pm.queryIntentActivities(intent, 0)){
            AppData appData = new AppData(ri.loadLabel(pm).toString(), ri.loadIcon(pm));
            appList.add(appData);
        }

        // 알파벳 순 정렬
        Collections.sort(appList, new Comparator<AppData>() {
            @Override
            public int compare(AppData a, AppData b) {
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.getName(),
                        b.getName());
            }
        });

        //  appList 출력하는 리스트뷰 구현
//        ListView listView = (ListView)view.findViewById(R.id.listView);
//        listView.setAdapter(new ArrayAdapter<ResolveInfo>(getActivity(), android.R.layout.simple_list_item_1,
//                appList){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView tv = (TextView)view;
//                ResolveInfo ri = getItem(position);
//                tv.setText(ri.loadLabel(pm));
//                return view;
//            }
//        });

        // appList 출력하는 RecyclerView 구현
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appList);
        mAdapter = new AppListAdapter();
        mAdapter.setItems(appList);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }


}
