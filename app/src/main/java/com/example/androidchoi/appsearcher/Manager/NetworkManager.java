package com.example.androidchoi.appsearcher.Manager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidchoi.appsearcher.Model.SignInData;
import com.google.gson.Gson;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private static NetworkManager instance;
    RequestQueue request = Volley.newRequestQueue(MyApplication.getContext());
    private Gson gson;

    // 싱글톤 인스턴스 get Method
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(MyApplication.getContext()),
                CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);
        gson = new Gson();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(String error);
    }

    private static final String SERVER = "http://52.78.29.249";

    //로그인
    private static final String SIGN_IN = SERVER + "/signin";
    public void signIn(final String email, final String pw, final OnResultListener<SignInData> listener){
        request.add(new StringRequest(Request.Method.POST, SIGN_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SignInData signInData = gson.fromJson(response, SignInData.class);
                        listener.onSuccess(signInData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("Error", volleyError.getMessage());
                        listener.onFail(volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", pw);
                return params;
            }
        });
    }
}
