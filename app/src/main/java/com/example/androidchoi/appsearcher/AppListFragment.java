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


/**
 * A simple {@link Fragment} subclass.
 */
public class AppListFragment extends Fragment{

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    RecyclerView mRecyclerView;
    AppListCursorAdapter mAdapter;

    public AppListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_app_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appList);

        db = dbHelper.getReadableDatabase(); // 읽기 가능하도록 db 객체 불러오기
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        // db에 applist가 생성되지 않은 경우 applist insert
        if(c.getCount() == 0){
            insertAppListInDB();
            c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        }
        mAdapter = new AppListCursorAdapter(c);

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
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // Vertical RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    //Local DB에 appList data를 넣는 메소드
    public void insertAppListInDB(){
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
        for(AppData appData : appList){
            insert(appData.getName(), appData.getPackageName(), appData.getActivityName());
        }
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

    public void deleteAll(){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
//        Log.i("db", name + "is deleted.");
    }

    public void select(){
        db = dbHelper.getReadableDatabase(); // 읽기 가능하도록 db 객체 불러오기
//        String[] columns = {DatabaseHelper.COLUMN_NAME
//                            ,DatabaseHelper.COLUMN_PACKAGE_NAME
//                            ,DatabaseHelper.COLUMN_ACTIVITY_NAME};
//        String whereStr = "name = ?";
//        String[] whereParams = {"CLiP"};
//        Cursor c = db.query(DatabaseHelper.TABLE_NAME, columns,
//                whereStr, whereParams, null, null, null);
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        int recordCount = c.getCount();
        Log.i("cursor count : ", recordCount + "");

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
