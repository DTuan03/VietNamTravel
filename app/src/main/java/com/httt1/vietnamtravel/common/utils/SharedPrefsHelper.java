package com.httt1.vietnamtravel.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    private static final String PREFS_NAME = "com.httt1.vietnamtravel.PREFERENCES";
    private final SharedPreferences sharedPreferences;
    public SharedPrefsHelper(Context context) {
       sharedPreferences = (SharedPreferences) context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //Luu thong tin dang nhap
    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //Lay thong tin dang nhap
    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    //Xoa thong tin dang nhap
    public void removeString(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
