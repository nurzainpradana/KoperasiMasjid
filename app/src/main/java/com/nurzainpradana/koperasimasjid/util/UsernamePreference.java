package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UsernamePreference {

    private static final String PREFS_NAME = "user_pref";

    private static final String USERNAME = "username";

    private SharedPreferences sharedPreferences;

    public UsernamePreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setUsernameSF(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public String getUsernameSF() {
        return sharedPreferences.getString(USERNAME, "");
    }
}
