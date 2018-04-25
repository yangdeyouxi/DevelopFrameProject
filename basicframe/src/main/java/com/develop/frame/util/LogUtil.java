package com.develop.frame.util;

import android.util.Log;

/**
 * Created by zengh on 2018/1/31.
 */

public class LogUtil {

    private static final String GAMELOANS = "gameLoans";

    public static void log(String msg) {
        Log.d(GAMELOANS, msg);
    }

    public static void logi(String msg) {
        Log.i(GAMELOANS, msg);
    }

}
