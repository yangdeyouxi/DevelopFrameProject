package com.develop.frame;

import android.app.Application;

import com.develop.frame.cache.GlobalCache;
import com.develop.frame.network.RxHttpUtils;
import com.develop.frame.util.ActivityUtils;
import com.develop.frame.util.CrashHandler;
import com.develop.frame.util.LanguageUtil;
import com.develop.frame.util.SPUtils;
import com.develop.frame.util.SystemUtil;
import com.develop.frame.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.Locale;

/**
 * Created by yangjh on 2018/4/25.
 */

public class FrameInitCenter {

    public static String DEVICEID_KEY = "deviceId";
    public static String WALLET_ADDRESS_KEY = "walletAddress";

    public static void init(Application context,String basurl){
        CrashHandler.getInstance().init(context);

        //Utils初始化，在调用各种Utils时不需再传入context,简化调用。
        Utils.init(context);
        GlobalCache.mApplication = context;

        /* 如需要管理Activity 关闭注册ActivityLifeCycleCallbacks;
        如关闭所有窗体，调用ActivityUtils.finishAllActivities()即可*/
        context.registerActivityLifecycleCallbacks(ActivityUtils.mLifeCycleCallbacks);

        //设置语言  因为每次app完全退出后重新启动,Local并不会被保存
        String localLanguageType = SPUtils.getParam(context, "language", "");
        if ("zh".equals(localLanguageType)) {
            LanguageUtil.changeLanguageType( Locale.SIMPLIFIED_CHINESE);
        } else if ("en".equals(localLanguageType)) {
            LanguageUtil.changeLanguageType(Locale.ENGLISH);
        }

        initHeader(context);
        initHttpUtils(context,basurl);
        initLogger();
    }

    private static void initHeader(Application context) {
        GlobalCache.mApplication = context;
        GlobalCache.mAppVersionCode = SystemUtil.getAppVersionCode();
        GlobalCache.mAppVersionName = SystemUtil.getAppVersionName();
        GlobalCache.mAppBuildCode = SystemUtil.getAppVersionCode();
        GlobalCache.mPlatformVersion = SystemUtil.getSystemVersion();
        GlobalCache.mDevideManufactuer = SystemUtil.getDeviceBrand();
        GlobalCache.mDevideModel = SystemUtil.getSystemModel();
        GlobalCache.mCurrentTimeZone = SystemUtil.getCurrentTimeZone();
        GlobalCache.mDeviceId = SPUtils.getParam(context, DEVICEID_KEY, "");
        GlobalCache.mWalletAddress = SPUtils.getParam(context, WALLET_ADDRESS_KEY, "");
    }

    private static void initHttpUtils(Application context,String baseUrl){

        RxHttpUtils.init(context);
        RxHttpUtils.getInstance()
                .config()
                .setBaseUrl(baseUrl)
                .setCookie(true)
                .setReadTimeout(10)
                .setWriteTimeout(10)
                .setConnectTimeout(10)
                .setLog(true);

    }
    private static void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("ComicDebug")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            // 控制是否输出，Release不输出 log
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

}
