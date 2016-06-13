package com.example.androidchoi.appsearcher;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Adapter.AppListPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppListContainerFragment extends Fragment {

    private AppListPagerAdapter mAppListPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public AppListContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_list_container, container, false);

        // 탭 설정
        mAppListPagerAdapter = new AppListPagerAdapter(getChildFragmentManager());
        mAppListPagerAdapter.addFragment(new AppAllListFragment());
        mAppListPagerAdapter.addFragment(new AppBookmarkedListFragment());
        mAppListPagerAdapter.addFragment(new AppRecommendListFragment());
        mAppListPagerAdapter.setTabList(
                new String[]{getString(R.string.app_all_list),
                             getString(R.string.app_bookmarked_list),
                             getString(R.string.app_recommend_list)}); // 탭 이름 설정
        // 뷰페이저 설정
        mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAppListPagerAdapter);
        mViewPager.setOffscreenPageLimit(mAppListPagerAdapter.getCount());
        mTabLayout = (TabLayout)view.findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", "position" + position);
                MainActivity mainActivity = ((MainActivity)getActivity());
                switch (position){
                    case 0:
                        ((AppAllListFragment)mAppListPagerAdapter.getItem(position)).filteringAppList("");
                        mainActivity.showSearchMenu();
                        break;
                    case 1:
                        ((AppBookmarkedListFragment)mAppListPagerAdapter.getItem(position)).refreshList();
                        mainActivity.closeSearchView();
                        mainActivity.hideSearchMenu();
                        break;
                    case 2:
                        mainActivity.closeSearchView();
                        mainActivity.hideSearchMenu();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public Fragment getCurrentFragment(){
//        Log.i("getCurrentFragment", getCurrentFragment().toString());
        return mAppListPagerAdapter.getItem(mViewPager.getCurrentItem());
    }


}
