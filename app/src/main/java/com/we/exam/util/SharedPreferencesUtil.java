package com.we.exam.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wangjinxing on 2016/11/22.
 */
public class SharedPreferencesUtil {

    /**
     * 存一个boolean值
     */
    public static void setBoolean(Context context, String key, boolean defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, defVelues);
        editor.commit();
    }

    /**
     * 取一个boolean值，可以设置默认值
     */
    public static boolean getBoolean(Context context, String key, boolean defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defVelues);
    }

    /**
     * 存一个字符串类型的数据
     */
    public static void setString(Context context, String key, String defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, defVelues);
        editor.commit();
    }

    /**
     * 取一个字符串类型的数据,默认是空
     */
    public static String getString(Context context, String key, String defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defVelues);
    }

    /**
     * 存一个数字
     */
    public static void setInt(Context context, String key, int defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, defVelues);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defVelues) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defVelues);
    }

    /**
     * 退出登入时清除用户信息
     */
    public static void clearCacheInfo(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

}
