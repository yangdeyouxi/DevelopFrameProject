package com.develop.frame.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2017/11/28.
 *
 * 当前App的信息及状态
 */

public class AppUtil {

    private AppUtil(){
        throw new UnsupportedOperationException("u can't instantiate me ...");
    }


    /**
     * 关闭App
     */
    public static void exitApp(){
        List<Activity> activityList = ActivityUtils.mActivityList;
        for (Activity activity:activityList){
            activity.finish();
            //TODO check if has bugs.
            activityList.remove(activity);
        }
        System.exit(0);
    }

    /**
     * 获取App包名
     * @return
     */

    public static String getAppPackageName(){
        return Utils.getApp().getPackageName();
    }

    /**
     * 获取 App名称
     * @return
     */
    public static String getAppName(){
        try{
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getAppPackageName(),0);
            return pi == null ? null :pi.applicationInfo.loadLabel(pm).toString();
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本名称
     * @return
     */
    public static String getAppVersionName(){
        try{
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getAppPackageName(),0);
            return pi == null ? null :pi.versionName.toString();
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App 版本码
     * @return
     */

    public static int getAppVersionCode(){
        try{
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getAppPackageName(),0);
            return pi == null ? -1 :pi.versionCode;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean isAppForeground(){
        ActivityManager manager= (ActivityManager) Utils.getApp()
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() ==0 )return false;
        for (ActivityManager.RunningAppProcessInfo aInfo:info){
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }
    /**
     * 检测服务是否启动
     */
    public static boolean isServiceStarted(Context context, String className) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (ActivityManager.RunningServiceInfo info : runningService) {
            if (info.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }



}
