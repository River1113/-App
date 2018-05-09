package com.lzx.listenmovieapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 此类描述的是:封装了sp的操作类
 *
 * @author: road
 */
public class SharedPrefsUtil {
    private static final String SHAREDPREFS_FILE_NAME = "listen_movie";
    private static SharedPreferences sp;

    /**
     * ̬保存boolean类型的参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean类型的参数̬
     *
     * @param context
     * @param key
     * @param defboolean
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defboolean) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defboolean);
    }

    /**
     * 保存string类型的参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取string类型的参数
     *
     * @param context
     * @param key
     * @param defstring
     * @return
     */
    public static String getString(Context context, String key, String defstring) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        return sp.getString(key, defstring);
    }

    /**
     * 保存int类型的参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 获取int类型的参数
     *
     * @param context
     * @param key
     * @param defInt
     * @return
     */
    public static int getInt(Context context, String key, int defInt) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        return sp.getInt(key, defInt);
    }

    /**
     * 保存long类型的参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 获取long类型的参数
     *
     * @param context
     * @param key
     * @param defInt
     * @return
     */
    public static long getLong(Context context, String key, long defInt) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        return sp.getLong(key, defInt);
    }

    /**
     * 删除 对应key 上的value , 删除后的值为 null
     *
     * @param key 对应的Key
     */
    public static void removeSP(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREFS_FILE_NAME, context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

}
