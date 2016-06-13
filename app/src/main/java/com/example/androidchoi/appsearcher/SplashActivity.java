package com.example.androidchoi.appsearcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                goMain();
                goLogin();
            }
        }, 1000);
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
}
