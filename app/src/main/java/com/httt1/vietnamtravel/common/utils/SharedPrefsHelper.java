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
    public void putInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //Lay thong tin dang nhap
    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    } // kh co gi mac dinh tra ve 0

    //Xoa thong tin dang nhap
    public void removeString(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
