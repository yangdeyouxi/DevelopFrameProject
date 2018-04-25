package com.develop.frame.util;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by sam on 2018/3/23.
 */

public class Utils {

    private static Application mApplication;

    private Utils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull final Application app){
        Utils.mApplication = app;
    }

    /**
     * 获取 Application
     * @return
     */
    public static Application getApp(){
        if (mApplication != null) return mApplication;
        throw new NullPointerException("u should init first");
    }
}
