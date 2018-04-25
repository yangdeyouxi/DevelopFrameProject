package com.develop.frame.cache;

import android.app.Application;
import android.text.TextUtils;

import com.develop.frame.util.EncryptUtils;
import com.develop.frame.util.SPUtils;
import com.develop.frame.util.SystemUtil;

/**
 * Created by zengh on 2018/4/11.
 */

public class GlobalCache {

    public static String mAppId = "123456";

    public static Application mApplication;

//    app_version	应用版本
//    app_build	应用构建版本
//    platform_name	平台名称
//    platform_version	平台版本
//    devide_manufactuer	设备制造商
//    device_model	设备型号

    public static int mAppVersionCode;
    public static String mAppVersionName;
    public static int mAppBuildCode;
    public static String mPlatformVersion;
    public static String mPlatformName = "Android";
    public static String mDevideManufactuer; // 设置制造商
    public static String mDevideModel; // 设备型号

    public static String mDeviceId; // 设备码。
    public static String mPlmn = ""; //

    public static String mCurrentTimeZone; // 当前时区。

    public static String mWalletAddress;

    public static int TIME_OFFSET;

    public static String getUserAgent() {
        String userAgent = String.format("acgn/bixy/%d/%d (%s/%s; %s/%s)",
                mAppVersionCode,
                mAppBuildCode,
                mPlatformName,
                mPlatformVersion,
                mDevideManufactuer,
                mDevideModel);
        return userAgent;
    }

    public static String getNonce() {
        return EncryptUtils.encryptMD5ToString(System.currentTimeMillis()+"", "bixyandroidios");
    }

    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000 - GlobalCache.TIME_OFFSET;
    }

    public static int getNetworkStateNum() {
        return SystemUtil.getNetworkStateNum(mApplication);
    }

    public static String getCurrentLanguage() {
        String currentLanguage = SPUtils.getParam(mApplication, "language", "");
        if (TextUtils.isEmpty(currentLanguage)) {
            currentLanguage = GlobalCache.mApplication.getResources().getConfiguration().locale.getLanguage();
        }
        return currentLanguage;
    }

}
