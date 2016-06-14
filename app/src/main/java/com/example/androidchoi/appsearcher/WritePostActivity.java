package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.PostData;

import java.util.Calendar;

public class WritePostActivity extends AppCompatActivity {

    EditText mEditTextTitle;
    EditText mEditTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_search);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorText));
        setSupportActionBar(toolbar);

        mEditTextTitle = (EditText)findViewById(R.id.editText_post_title);
        mEditTextContent = (EditText)findViewById(R.id.editText_post_content);

        //제목 EditText에 자동 포커스 및 키보드 세팅
        mEditTextTitle.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditTextTitle, InputMethodManager.SHOW_IMPLICIT);

        // 터치 인터셉터
        FrameLayout touchInterceptor = (FrameLayout)findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mEditTextTitle.isFocused()) {
                        Rect outRect = new Rect();
                        mEditTextTitle.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditTextTitle.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }else if (mEditTextContent.isFocused()) {
                        Rect outRect = new Rect();
                        mEditTextContent.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditTextContent.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }

    // 게시글 저장 요청 메소드
    public void writePost(){
        String appName = mEditTextTitle.getText().toString();
        String appEvaluation = mEditTextContent.getText().toString();
        long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
        NetworkManager.getInstance().writePost(appName, "", appEvaluation, currentTimeStamp,
                new NetworkManager.OnResultListener<PostData>() {
            @Override
            public void onSuccess(PostData result) {
                setResult(RESULT_OK);
                finish();
                Toast.makeText(MyApplication.getContext(), "게시글이 작성 되었습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // 게시글 저장 요청
            writePost();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
