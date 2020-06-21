package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nurzainpradana.koperasimasjid.model.Member;

public class MemberPreference {

    private static final String PREFS_NAME = "user_pref";

    private static final String USERNAME = "username";

    private SharedPreferences sharedPreferences;

    public MemberPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setMember(Member member) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, member.getmUsername());
        editor.apply();
    }

    public Member getMember() {
        Member member = new Member();
        member.setmUsername(sharedPreferences.getString(USERNAME, ""));
        return member;
    }


}
