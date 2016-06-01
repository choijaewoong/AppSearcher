package com.example.androidchoi.appsearcher.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppListPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;

    private String[] mTabTitles = new String[PAGE_COUNT];
    private List<Fragment> mFragments = new ArrayList<>();

    public AppListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // 탭 프래그먼트 추가
    public void addFragment(Fragment fragment){
        mFragments.add(fragment);
    }

    // 탭 이름 설정 메소드
    public void setTabList(String strings[]){
        mTabTitles = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }


}

