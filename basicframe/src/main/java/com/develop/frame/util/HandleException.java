package com.develop.frame.util;

import android.app.Activity;
import android.widget.Toast;

import com.develop.frame.R;


/**
 * Created by zengh on 2018/1/31.
 */

public class HandleException {

    public static boolean getActivityDestoryed(Activity activity){
        if(android.os.Build.VERSION.SDK_INT < 17){
            return activity.isFinishing();
        }else{
            return activity.isDestroyed();
        }
    }


    public static void showWrong(Activity activity){
        try {
            Toast.makeText(activity,activity.getResources().getString(R.string.unknow_wrong_try_later), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            HandleException.printException(e);
        }
    }

    public static void showWrong(Activity activity,String msg){
        try {
            Toast.makeText(activity,activity.getResources().getString(R.string.unknow_wrong_try_later), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            HandleException.printException(e);
        }
        LogUtil.logi("HasWrong:" + msg);
    }

    public static void showSelfWrong(Activity activity,String toastContent,String msg){
        try {
            Toast.makeText(activity, toastContent, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            HandleException.printException(e);
        }
        LogUtil.logi("HasWrong:" + msg);
    }

    public static void printException(Exception e){
        e.printStackTrace();
        LogUtil.logi("HasWrong:" + e.getMessage());
    }

//    private static void upload(Exception errorInfo){
//        StatisticsModel model = new StatisticsModel();
//        model.level = StatisticsModel.LEVEL_WARN;
//        model.parseException(errorInfo);
//        model.time = System.currentTimeMillis();
//        model.title = "WARN";
//
//        SkillStatisticsManager.getInstance().startUploadLog(model);
//    }

}
