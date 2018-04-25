package com.develop.frame.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zengh on 2018/3/27.
 */

public class SPUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static <T> void setParam(Context context , String key, T object){

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static <T> T getParam(Context context , String key, T defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            String result = sp.getString(key, (String)defaultObject);
            return (T)result;
        }
        else if("Integer".equals(type)){
            Integer result = sp.getInt(key, (Integer) defaultObject);
            return (T)result;
        }
        else if("Boolean".equals(type)){
            Boolean result = sp.getBoolean(key, (Boolean) defaultObject);
            return (T)result;
        }
        else if("Float".equals(type)){
            Float result = sp.getFloat(key, (Float) defaultObject);
            return (T)result;
        }
        else if("Long".equals(type)){
            Long result = sp.getLong(key, (Long) defaultObject);
            return (T)result;
        }
        return null;
    }

}
