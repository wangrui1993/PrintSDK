package com.handset.sdktool.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: SPUtil
 * @author: wr
 * @date: 2023/9/21 15:46
 * @Description:作用描述
 */
public class SPUtil {
    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor shareEditor;

    private static SPUtil instance;

    private SPUtil(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        shareEditor = sharedPreferences.edit();
        shareEditor.apply();
    }

    public static void init(Context context) {
        init(context, context.getApplicationInfo().loadLabel(context.getPackageManager()).toString());
    }

    public static void init(Context context, String name) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil(context, name);
                }
            }
        }
    }

    public static SPUtil getInstance() {
        return instance;
    }

    public static String getStringParam(String key) {
        return getStringParam(key, null);
    }

    public static String getStringParam(String key, String defaultString) {
        return instance.sharedPreferences.getString(key, defaultString);
    }

    public static void saveParam(String key, String value) {
        instance.shareEditor.putString(key, value).commit();
    }

    public static boolean getBooleanParam(String key) {
        return getBooleanParam(key, false);
    }

    public static boolean getBooleanParam(String key, boolean defaultBool) {
        return instance.sharedPreferences.getBoolean(key, defaultBool);
    }

    public static void saveParam(String key, boolean value) {
        instance.shareEditor.putBoolean(key, value).commit();
    }

    public static int getIntParam(String key) {
        return getIntParam(key, 0);
    }

    public static int getIntParam(String key, int defaultInt) {
        return instance.sharedPreferences.getInt(key, defaultInt);
    }

    public static void saveParam(String key, int value) {
        instance.shareEditor.putInt(key, value).commit();
    }

    public static long getLongParam(String key) {
        return getLongParam(key, 0);
    }

    public static long getLongParam(String key, long defaultInt) {
        return instance.sharedPreferences.getLong(key, defaultInt);
    }

    public static void saveParam(String key, long value) {
        instance.shareEditor.putLong(key, value).commit();
    }

    public static void removeKey(String key) {
        instance.shareEditor.remove(key).commit();
    }

    public static void clear() {
        instance.shareEditor.clear().commit();
    }
}
