package com.example.androidchoi.appsearcher;


import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Adapter.AppDefaultListCursorAdapter;
import com.example.androidchoi.appsearcher.Model.AppData;
import com.example.androidchoi.appsearcher.ViewHolder.AppDefualtItemViewHolder;
import com.example.androidchoi.appsearcher.ViewHolder.AppItemViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppAllListFragment extends Fragment{

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    RecyclerView mRecyclerView;
    AppDefaultListCursorAdapter mAdapter;
    CustomPopupWindow popup;

    public AppAllListFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_app_all_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_appList);

        db = dbHelper.getReadableDatabase(); // 읽기 가능하도록 db 객체 불러오기
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        // db에 applist가 생성되지 않은 경우 applist insert
        if(c.getCount() == 0){
            initAppList();
            c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        }
        mAdapter = new AppDefaultListCursorAdapter(c);

        // item 클릭시 해당 앱 실행
        mAdapter.setOnItemClickListener(new AppItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String packageName, String activityName, int position) {
                ((MainActivity)getActivity()).closeSearchView(); // 앱 클릭 시 SearchView 닫음.
                try{
                    //해당 App의 PackageName, activityName을 이용해 App 실행
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName(packageName, activityName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(getActivity(),"존재하지 않는 Application 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // popup 버튼 클릭시 popup 메뉴 생성
        mAdapter.setOnPopUpButtonClickListener(new AppDefualtItemViewHolder.OnPopUpButtonClickListener() {
            @Override
            public void onPopUpButtonClick(View view, String packageName, String activityName, int position) {
//                Toast.makeText(getContext(), packageName + "/" + activityName + "/ " + position, Toast.LENGTH_SHORT).show();
                if (popup == null) {
                    popup = new CustomPopupWindow(getActivity());
                    popup.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), android.R.color.transparent));
                }
                if (popup.isShowing()) {
                    popup.dismiss();
                } else {
                    popup.setAppInfo(packageName, activityName);
                    popup.showAsDropDown(view, 0, -50);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // Vertical RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    //Local DB에 appList data를 넣는 메소드
    public void initAppList(){
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
            insert(appData);
        }
    }

    // 검색을 이용하여 App Filtering 하는 Method
    public void filteringAppList(String str){
        Cursor cursor = select(str);
        mAdapter.changeCursor(cursor);
    }

    // Insert Data
    public void insert(AppData appData){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, appData.getName());
        values.put(DatabaseHelper.COLUMN_IMAGE, appData.getImage());
        values.put(DatabaseHelper.COLUMN_PACKAGE_NAME, appData.getPackageName());
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, appData.getActivityName());
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    // Update Data
    public void update(AppData appData){
        db = dbHelper.getWritableDatabase(); // 쓰기 가능하도록 db 객체 불러오기
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, appData.getName());
        values.put(DatabaseHelper.COLUMN_IMAGE, appData.getImage());
        values.put(DatabaseHelper.COLUMN_PACKAGE_NAME, appData.getPackageName());
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, appData.getActivityName());
        db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_NAME + "=?", new String[]{appData.getName()});
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
        Log.i("db", "All deleted.");
    }

    public Cursor select(String str){
        db = dbHelper.getReadableDatabase(); // 읽기 가능하도록 db 객체 불러오기
        String[] columns = {"_id", DatabaseHelper.COLUMN_NAME,
                            DatabaseHelper.COLUMN_IMAGE,
                            DatabaseHelper.COLUMN_PACKAGE_NAME,
                            DatabaseHelper.COLUMN_ACTIVITY_NAME};
        String whereStr = "name LIKE ?";
        String[] whereParams = {"%" + str + "%"};
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, columns,
                whereStr, whereParams, null, null, null);

//        int recordCount = c.getCount();
//        Log.i("cursor count : ", recordCount + "");
//
//        while(c.moveToNext()){
//            int _id = c.getInt(c.getColumnIndex("_id"));
//            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
//            String packageName = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_PACKAGE_NAME));
//            String activityName = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_ACTIVITY_NAME));
//            Log.i("db : ", + _id + ", name : " + name + ", package : " + packageName
//                    + ", activity : " + activityName);
//        }
        return c;
    }
}
