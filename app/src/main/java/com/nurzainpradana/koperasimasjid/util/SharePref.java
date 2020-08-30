package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePref {

    private SharedPreferences sharedPreferences;

    public SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(Const.ID_USER_KEY, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }


}


