package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG_APP_LIST = "tagAppList";
    public static final String TAG_APP_BOARD = "tagAppBoard";
    public static final String TAG_SETTING = "tagSetting";

    private MenuItem mSearchMenu;
    private boolean mIsSearchOpened = false;
    private EditText mEditTextSearch;
    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.sliding_menu_main); //Setting SlidingMenu

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_search);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorText));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 홈 메뉴 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_list);

        if (savedInstanceState == null) {
//          getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new MenuFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AppListContainerFragment(), TAG_APP_LIST).commit();
        }

        //슬라이딩 메뉴 설정
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindWidthRes(R.dimen.sliding_menu_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow_sliding_menu);
        mSlidingMenu.setShadowWidthRes(R.dimen.sliding_menu_shadow_width);
        mSlidingMenu.setFadeDegree(0.3f); //블러처리 해제
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = getLayoutInflater().inflate(R.layout.sliding_menu_header, null);
        navigationView.addHeaderView(navHeaderView);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchMenu = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case android.R.id.home:
                toggle();
                return true;
            case R.id.action_search:
                handleSearchView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleSearchView(){
        if(mIsSearchOpened){ // Serach Bar 열려 있는 경우
            closeSearchView();
        } else { // Serach Bar 닫혀 있는 경우
            openSearchView();
        }
    }
    public void closeSearchView(){
        ActionBar actionbar = getSupportActionBar();
        // editText CustomView에서 TitleView 로 전환
        actionbar.setDisplayShowCustomEnabled(false);
        actionbar.setDisplayShowTitleEnabled(true);
        // Hide Keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        //search icon 으로 변경
        mSearchMenu.setIcon(ContextCompat.getDrawable(this, R.drawable.icon_search));
        mIsSearchOpened = false;
    }
    public void openSearchView(){
        ActionBar actionbar = getSupportActionBar();
        // TitleView에서 editText CustomView 로 전환
        actionbar.setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(R.layout.view_search_bar, null);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        actionbar.setCustomView(view, layoutParams);
        actionbar.setDisplayShowTitleEnabled(false);

        // Setting EditTextView
        mEditTextSearch = (EditText)actionbar.getCustomView().findViewById(R.id.editText_search); //the text editor
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                AppAllListFragment appAllListFragment = (AppAllListFragment)mAppListPagerAdapter.getItem(mViewPager.getCurrentItem());
                AppListContainerFragment fragment = (AppListContainerFragment)getSupportFragmentManager().findFragmentByTag(TAG_APP_LIST);
                AppAllListFragment appAllListFragment = (AppAllListFragment)fragment.getCurrentFragment();
                appAllListFragment.filteringAppList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditTextSearch.requestFocus();
        // Show Keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
        //close icon 으로 변경
        mSearchMenu.setIcon(ContextCompat.getDrawable(this, R.drawable.icon_close));
        mIsSearchOpened = true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        showContent();
        return true;
    }
}
