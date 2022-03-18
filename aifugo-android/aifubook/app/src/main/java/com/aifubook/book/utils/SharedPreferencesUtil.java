package com.aifubook.book.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 *  
 *
 * @dec : 绿城轻量级存储
 *  @author ListKer-Hlg
 *  @time   2018/4/16 0016 : 10:28 
 *  @return  none
 */
public class SharedPreferencesUtil {

    public static final String KEY_IS_FIRST = "is_first";


    public static final String FILE_NAME = "share_data";


    private SharedPreferencesUtil() {

    }

    int s;
    Object sb[] = new Object[10];

    public Object getS() {
        if (s == 0)
            return null;
        return sb[--s];
    }

    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (value == null) {
            throw new IllegalArgumentException("value can not be null!");
        }
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }

    public static <T> T get(Context context, String key, T defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return (T) String.valueOf(sp.getString(key, (String) defaultValue));
        } else if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(sp.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(sp.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) Float.valueOf(sp.getFloat(key, (Float) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) Long.valueOf(sp.getLong(key, (Long) defaultValue));
        }
        return null;
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
