package com.example.Adhiya.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataProccessor {
    private static Context context;
    public DataProccessor(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "MySharedPref";
    public static String getString(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key,"");
    }
    public static String getToken(){
        return getString("token");
    }
}
