package com.jichang.jichangprojectlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ: ShardPreferencesUtils, if you want to invoke ShardPreferences to save your data, you can
 * do it with this class
 */

public class SpUtils {

    private static final String FILE_NAME = "shard_data";

    /**According to you have given data type(object), choose different method to save data
     * @param context context
     * @param key Key need you give
     * @param object Data will be saved
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**According to you have given data type(defaultObject), choose different method to request data
     * @param context context
     * @param key Key need you give
     * @param defaultObject Default data
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * SharedPreferences compat class, can deal with some API not contain 'apply' method
     */
    private static class SharedPreferencesCompat {

        private static final Method SApplyMethod = findApplyMethod();

        /**Use reflect technology to get a method from Editor
         * @return
         */
        private static Method findApplyMethod() {
            Class clz = SharedPreferences.Editor.class;
            try {
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**if get 'apply' method, call it; Otherwise, call commit
         * @param editor
         */
        static void apply(SharedPreferences.Editor editor) {
            if (SApplyMethod != null) {
                try {
                    SApplyMethod.invoke(editor);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            editor.commit();
        }
    }

    /**According to you have given key, remove an element from sp
     * @param context context
     * @param key Key need you give
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**Clear the sp
     * @param context context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**According to you have given key, Query the element whether be contained in sp
     * @param context context
     * @param key Key need you give
     * @return contain?
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**Get all of sp
     * @param context context
     * @return Map, contains all elements of sp
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
