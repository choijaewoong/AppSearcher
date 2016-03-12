package com.example.androidchoi.appsearcher;


import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Model.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.sql.DriverManager.println;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppListFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    RecyclerView mRecyclerView;
    AppListAdapter mAdapter;

    public AppListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(getActivity());

        //insert appList in Database
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
        List<ResolveInfo> riList = pm.queryIntentActivities(intent, 0);

        // AppData 객체 리스트 세팅
        List<AppData> appList = new ArrayList<>();
        for(ResolveInfo ri : riList){
            AppData appData = new AppData(ri, pm);
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

        // appList를 이용하여 RecyclerView , AppListAdapter 세팅
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appList);
        mAdapter = new AppListAdapter();
        mAdapter.setItems(appList);
        // item 클릭시 해당 앱 실행

        mAdapter.setOnItemClickListener(new AppItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String packageName, String activityName, int position) {
                ((AppListActivity)getActivity()).closeSearchView(); // 앱 클릭 시 SearchView 닫음.
                //해당 App의 PackageName, activityName을 이용해 App 실행
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName(packageName, activityName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                Toast.makeText(getActivity(), "packagename : " + packageName + "/ activityname : " + activityName, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // Vertical RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    // Insert Data
    public void insert(String name, String packageName, String activityName){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PACKAGE_NAME, packageName);
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, activityName);
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    // Update Data
    public void update(String name, String packageName, String activityName){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PACKAGE_NAME, packageName);
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, activityName);
        db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_NAME + "=?", new String[]{name});
    }

    // Delete Data
    public void delete(String name){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_NAME + "=?", new String[]{name});
        Log.i("db", name + "is deleted.");
    }

    public void select(){
        String[] columns = {DatabaseHelper.COLUMN_NAME
                            ,DatabaseHelper.COLUMN_PACKAGE_NAME
                            ,DatabaseHelper.COLUMN_ACTIVITY_NAME};
        String whereStr = "name = ?";
        String[] whereParams = {"CLiP"};

//        Cursor c = db.query(DatabaseHelper.TABLE_NAME, columns,
//                whereStr, whereParams, null, null, null);
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        int recordCount = c.getCount();
        println("cursor count : " + recordCount + "\n");

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String packageName = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_PACKAGE_NAME));
            String activityName = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_ACTIVITY_NAME));
            Log.i("db : ", + _id + ", name : " + name + ", package : " + packageName
                    + ", activity : " + activityName);
        }
    }


}
