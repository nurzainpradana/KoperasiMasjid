package com.nurzainpradana.koperasimasjid.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.nurzainpradana.koperasimasjid.MyApp;

public class SharePreferenceUtils {

    private static String PREFENCE_NAME = "koperasimasjid";
    private static SharePreferenceUtils sharePreferenceUtils;
    private SharedPreferences sharedPreferences;

    private SharePreferenceUtils(Context context) {
        PREFENCE_NAME = PREFENCE_NAME + context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFENCE_NAME, context.MODE_PRIVATE);
    }

    public static SharePreferenceUtils getInstance() {
        if (sharePreferenceUtils == null) {
            sharePreferenceUtils = new SharePreferenceUtils(MyApp.getContext());
        }
        return sharePreferenceUtils;
    }

    //login response
    public void saveString(String key, String val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getString(String key, String defVal) {
        return sharedPreferences.getString(key, defVal);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    //Clear all values from this preference
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void clearString(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
