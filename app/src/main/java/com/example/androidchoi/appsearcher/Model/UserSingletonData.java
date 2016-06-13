package com.example.androidchoi.appsearcher.Model;

/**
 * Created by Choi on 2016-06-14.
 */
public class UserSingletonData {
    private String email;
    private String name;

    private static UserSingletonData instance;
    public static UserSingletonData getInstance(){
        if(instance == null){
            instance = new UserSingletonData();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setUser(String email, String name){
        this.email = email;
        this.name = name;
    }
}
