package com.example.androidchoi.appsearcher.Manager;

import android.content.Context;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * Created by Choi on 2016-05-14.
 */
public class PersistentCookieStore implements CookieStore {

    private CookieStore mStore;
    private Context mContext;
    private Gson mGson;

    public PersistentCookieStore(Context context) {
        // prevent context leaking by getting the application context
        mContext = context.getApplicationContext();
        mGson = new Gson();

        // get the default in memory store and if there is a cookie stored in shared preferences,
        // we added it to the cookie store
        mStore = new CookieManager().getCookieStore();
//        String jsonSessionCookie = Prefs.getJsonSessionCookie(mContext);
//        if (!jsonSessionCookie.equals(Prefs.DEFAULT_STRING)) {
//            HttpCookie cookie = mGson.fromJson(jsonSessionCookie, HttpCookie.class);
//            mStore.add(URI.create(cookie.getDomain()), cookie);
//        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (cookie.getName().equals("sessionid")) {
            // if the cookie that the cookie store attempt to add is a session cookie,
            // we remove the older cookie and save the new one in shared preferences
            remove(URI.create(cookie.getDomain()), cookie);
//            Prefs.saveJsonSessionCookie(mContext, mGson.toJson(cookie));
        }

        mStore.add(URI.create(cookie.getDomain()), cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return mStore.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return mStore.getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return mStore.getURIs();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return mStore.remove(uri, cookie);
    }

    @Override
    public boolean removeAll() {
        return mStore.removeAll();
    }
}
