package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class AppListActivity extends AppCompatActivity {

    private MenuItem mSearchMenu;
    private boolean mIsSearchOpened = false;
    private EditText mEditTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        // App 화면 크기 설정
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_search);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorBackground));
        setSupportActionBar(toolbar);

        // Create Fragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(fragment == null){
            fragment = new AppListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
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
        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //Search Method
                    return true;
                }
                return false;
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
}
