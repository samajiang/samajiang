package com.example.android.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.URI;

public class SessionManager {
    private static final String PREF_NAME = "LoginSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_AVATAR = "avatat";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public void saveUserDetails( String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    public String setavatar(String uri){
        return editor.putString(KEY_AVATAR,uri).toString();
    }

    public String getAvatar(){
       return pref.getString(KEY_AVATAR,null);
    }
}
