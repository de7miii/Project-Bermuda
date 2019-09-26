package com.example.freelanceproject.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.freelanceproject.BusinessModel.User;

public class SaveSharedPreference {

    static final String LOGIN_STATUS = "logged_in";
    static final String USERNAME = "username";
    static final String ACC_TYPE = "acc_type";

    static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoginStatus(Context context, boolean loginStatus){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(LOGIN_STATUS, loginStatus);
        editor.apply();
    }

    public static boolean getLoginStatus(Context context){
        return getSharedPreference(context).getBoolean(LOGIN_STATUS, false);
    }

    public static void setUsername(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static String getUsername(Context context){
        return getSharedPreference(context).getString(USERNAME, "username");
    }

    public static void setAccType(Context context, int acc_type){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(ACC_TYPE, acc_type);
        editor.apply();
    }

    public static int getAccType(Context context){
        return getSharedPreference(context).getInt(ACC_TYPE, User.FREELANCER_ACCOUNT);
    }

}
