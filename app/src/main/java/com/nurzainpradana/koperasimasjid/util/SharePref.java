package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private SharedPreferences sharedPreferences;

    public SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(Const.USERNAME_KEY, Context.MODE_PRIVATE);
    }

    public void setUsernameSF(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Const.USERNAME_KEY, username);
        editor.apply();
    }

    public String getUsernameSF() {
        return sharedPreferences.getString(Const.USERNAME_KEY, "");
    }
}
