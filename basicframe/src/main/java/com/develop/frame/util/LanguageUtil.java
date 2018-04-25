package com.develop.frame.util;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.develop.frame.cache.GlobalCache;

import java.util.Locale;

/**
 * Created by yangjh on 2018/4/24.
 */

public class LanguageUtil {

    public static void changeLanguageType(Locale locale) {
        Resources resources = GlobalCache.mApplication.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        // 参考 https://developer.android.com/reference/android/content/res/Configuration.html
        if (Build.VERSION.SDK_INT >= 24) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, dm);
        }
    }

    public static Locale getLanguageType() {
        Resources resources = GlobalCache.mApplication.getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        if (Build.VERSION.SDK_INT >= 24) {
            return config.getLocales().get(0);
        } else {
            return config.locale;

        }
    }
}
