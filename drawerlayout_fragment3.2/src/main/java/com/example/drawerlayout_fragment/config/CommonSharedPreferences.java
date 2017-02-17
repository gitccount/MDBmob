package com.example.drawerlayout_fragment.config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CommonSharedPreferences {
    private static SharedPreferences splogin;
    private static Editor editor;
    private static String account, password, headurl;

    public static String getHead() {
        // return password;
        return splogin.getString("head", "");
    }

    public static void setHead(String head) {
        // CommonSharedPreferences.password = password;
        editor.putString("head", head).commit();
    }

    public static String getAccount() {
        return splogin.getString("account", "");
    }

    public static void setAccount(String account) {
        editor.putString("account", account).commit();
    }

    public static String getPassword() {
        // return password;
        return splogin.getString("password", "");
    }

    public static void setPassword(String password) {
        // CommonSharedPreferences.password = password;
        editor.putString("password", password).commit();
    }

    public static void init(Context context) {
        splogin = context.getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = splogin.edit();
    }

    public static boolean getAutologin() {
        return splogin.getBoolean("autologin", false);
    }

    public static void setAutologin(boolean islogin) {
        editor.putBoolean("autologin", islogin).commit();
    }

    public static boolean getRemenberPass() {
        return splogin.getBoolean("remPass", false);
    }

    public static void setRemenberPass(boolean islogin) {
        editor.putBoolean("remPass", islogin).commit();
    }
}
