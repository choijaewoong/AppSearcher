package com.example.androidchoi.appsearcher.Manager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidchoi.appsearcher.Model.AppList;
import com.example.androidchoi.appsearcher.Model.AppServerData;
import com.example.androidchoi.appsearcher.Model.AppServerDataLab;
import com.example.androidchoi.appsearcher.Model.PostData;
import com.example.androidchoi.appsearcher.Model.PostList;
import com.example.androidchoi.appsearcher.Model.RecommendList;
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

    // 즐겨 찾기 목록 가져오기
    private static final String SHOW_APP_LIST = SERVER + "/applist";
    public void showAppList(final OnResultListener<AppList> listener){
        request.add(new StringRequest(Request.Method.GET, SHOW_APP_LIST,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        AppList appList = gson.fromJson(s, AppList.class);
                        listener.onSuccess(appList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onFail(volleyError.getMessage());
                    }
                })
        );
    }

    // 앱 즐겨 찾기
    private static final String ADD_APP = SERVER + "/addapp";
    public void addApp(final String name, final String imageURL, final String packageName, final String activityName,
                       final OnResultListener<AppServerData> listener){
        request.add(new StringRequest(Request.Method.POST, ADD_APP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppServerDataLab appServerDataLab = gson.fromJson(response, AppServerDataLab.class);
                        listener.onSuccess(appServerDataLab.getAppServerData());
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
                params.put("name", name);
                params.put("image", "");
                params.put("package_name", packageName);
                params.put("activity_name", activityName);
                return params;
            }
        });
    }

    // 게시글 목록 가져오기
    private static final String SHOW_POST_LIST = SERVER + "/postlist";
    public void showPostList(final OnResultListener<PostList> listener){
        request.add(new StringRequest(Request.Method.GET, SHOW_POST_LIST,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String s) {
                                PostList postList = gson.fromJson(s, PostList.class);
                                listener.onSuccess(postList);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onFail(volleyError.getMessage());
                    }
                })
        );
    }

    // 추천 목록 가져오기
    private static final String SHOW_RECOMMEND_LIST = SERVER + "/recommendlist";
    public void showRecommendList(final OnResultListener<RecommendList> listener){
        request.add(new StringRequest(Request.Method.GET, SHOW_RECOMMEND_LIST,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String s) {
                                RecommendList recommendList = gson.fromJson(s, RecommendList.class);
                                listener.onSuccess(recommendList);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onFail(volleyError.getMessage());
                    }
                })
        );
    }

    // 게시글 작성 요청 메소드
    private static final String WRITE_POST = SERVER + "/writepost";
    public void writePost(final String appName, final String appImage, final String appEvaluation, final long writeDate, final OnResultListener<PostData> listener){
        request.add(new StringRequest(Request.Method.POST, WRITE_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PostData postData = gson.fromJson(response, PostData.class);
                        listener.onSuccess(postData);
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
                params.put("app_name", appName);
                params.put("app_image", appImage);
                params.put("app_evaluation", appEvaluation);
                params.put("write_date", String.valueOf(writeDate));
                return params;
            }



            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        });
    }

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
