package com.develop.frame.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sam on 2017/11/28.
 */


@SuppressLint("ApplySharedPref")
public class SPUtil {
    private static SimpleArrayMap<String,SPUtil> SP_UTIL_MAP=
            new SimpleArrayMap<>();

    private SharedPreferences sp;


    /**
     * 获取 SP实例
     * @return
     */
    public static SPUtil getInstance(){
        return getInstance("");
    }

    /**
     * 获取SP 实例
     * @param spName
     * @return
     */

    public static SPUtil getInstance(String spName){
        if (isSpace(spName)) spName = "spUtil";
        SPUtil spUtil = SP_UTIL_MAP.get(spName);
        if (spUtil == null){
            spUtil = new SPUtil(spName);
            SP_UTIL_MAP.put(spName,spUtil);
        }
        return spUtil;
    }

    private SPUtil(final String spName){
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }


    /**
     * SP 中写入 String
     * @param key
     * @param value
     */
    public  void putString(@NonNull final String key, @NonNull final String value ){
        putString(key,value,false);
    }

    /**
     *  SP 中写入String
     * @param key
     * @param value
     * @param isCommit //true 用commit 提交，false 用apply 提交。
     */

    public void putString(@NonNull final String key, @NonNull final String value, final boolean isCommit ){
        if (isCommit){
            sp.edit().putString(key,value).commit();
        }else{
            sp.edit().putString(key,value).apply();
        }
    }




    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }
    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return sp.getString(key, defaultValue);
    }


    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */
    public void putInt(@NonNull final String key, final int value) {
        putInt(key, value, false);
    }

    /**
     * SP中写入int
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void putInt(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    public void putLong(@NonNull final String key, final long value) {
        putLong(key, value, false);
    }

    /**
     * SP中写入long
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void putLong(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
    public void putFloat(@NonNull final String key, final float value) {
        putFloat(key, value, false);
    }

    /**
     * SP中写入float
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void putFloat(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */
    public void putBoolean(@NonNull final String key, final boolean value) {
        putBoolean(key, value, false);
    }

    /**
     * SP中写入boolean
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void putBoolean(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP中写入String集合
     *
     * @param key    键
     * @param values 值
     */
    public void putStringSet(@NonNull final String key, @NonNull final Set<String> values) {
        putStringSet(key, values, false);
    }

    /**
     * SP中写入String集合
     *
     * @param key      键
     * @param values   值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void putStringSet(@NonNull final String key, @NonNull final Set<String> values, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, values).commit();
        } else {
            sp.edit().putStringSet(key, values).apply();
        }
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code Collections.<String>emptySet()}
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * SP中移除该key
     *
     * @param key      键
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        clear(false);
    }

    /**
     * SP中清除所有数据
     *
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }



    public <T> void putObject(String key,T object){
        if (object == null) {
            remove(key,false);
            return;
        }
        else {
            Gson gson = new Gson();
            String strJson = gson.toJson(object);
            putString(key, strJson, false);
        }
    }
    public <T> void putObject(String key,T object,boolean isCommit){
        if (object == null)
        {
            remove(key,isCommit);
            return;
        }else {
            Gson gson = new Gson();
            String strJson = gson.toJson(object);
            putString(key, strJson, isCommit);
        }
    }

    public <T> T getObject(String key,Class<T> clazz){
        String strJson = sp.getString(key,null);
        if (null == strJson){
            return null;
        }
        T object = null;
        Gson gson = new Gson();
        try {
            object = gson.fromJson(strJson, clazz);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }

    public <T> void putObjectList(String key, List<T> objectList){
        if (null == objectList || objectList.size() <=0) return;
        Gson gson = new Gson();
        String strJson = gson.toJson(objectList);
        putString(key,strJson,false);


    }
    public <T> void putObjectList(String key, List<T> objectList,boolean isCommit){
        if (null == objectList || objectList.size() <=0) return;
        Gson gson = new Gson();
        String strJson = gson.toJson(objectList);
        putString(key,strJson,isCommit);
    }

    public <T> List<T> getObjectList(String key,Class<T> clazz){
        String strJson = sp.getString(key,null);
        if (TextUtils.isEmpty(strJson)){
            return null;
        }
        Gson gson = new Gson();
        List<T> objectList = new ArrayList<>();

        try{
            JsonArray jsonArray = new JsonParser().parse(strJson).getAsJsonArray();
            for(JsonElement jsonElement: jsonArray){
                objectList.add(gson.fromJson(jsonElement,clazz));
            }
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
        return objectList;
    }

    
    private static boolean isSpace(final String s){
        if (s == null) return true;
        for (int i = 0, len = s.length(); i<len; ++i){
            if (!Character.isWhitespace(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
