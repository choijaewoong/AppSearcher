package com.example.androidchoi.appsearcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Manager.PropertyManager;
import com.example.androidchoi.appsearcher.Model.SignInData;
import com.example.androidchoi.appsearcher.Model.UserData;
import com.example.androidchoi.appsearcher.Model.UserSingletonData;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String email = PropertyManager.getInstance().getEmail(); // 자동 로그인을 위해 프로퍼티 매니저에서 저장 되어 있는 이메일 확인
        if(!TextUtils.isEmpty(email)){  // 저장되어 있는 사용자 정보가 있는 경우
            String password = PropertyManager.getInstance().getPassword();
            signIn(email, password);
        }else { // 저장 되어 있는 사용자 정보가 없는 경우
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLogin();
                }
            }, 1000);
        }
    }
    Handler mHandler = new Handler(Looper.getMainLooper());

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void goLogin() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    public void signIn(final String email, final String password) {
        NetworkManager.getInstance().signIn(email, password, new NetworkManager.OnResultListener<SignInData>() {
            @Override
            public void onSuccess(SignInData result) {
                if (result.getMessage().equals(SignInFragment.MESSAGE_SUCCESS)) {
                    UserData user = result.getUser();
                    PropertyManager.getInstance().setEmail(email);
                    PropertyManager.getInstance().setPassword(password);
                    UserSingletonData.getInstance().setUser(user.getEmail(), user.getName());
                    goMain();
                } else {
                    goLogin();
                }
            }
            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "로그인 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                goLogin();
            }
        });
    }
}
