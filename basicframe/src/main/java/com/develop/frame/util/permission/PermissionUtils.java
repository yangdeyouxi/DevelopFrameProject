package com.develop.frame.util.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.develop.frame.util.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.Rationale;

import java.util.List;

/**
 * Created by zengh on 2018/1/31.
 */

public class PermissionUtils {

    public static PermissionUtils utils;

    public static PermissionUtils getInstance() {
        if (null == utils) {
            synchronized (PermissionUtils.class) {
                if (null == utils) {
                    utils = new PermissionUtils();
                }
            }
        }
        return utils;
    }

    private PermissionSetting mSetting;
    private Rationale mRationale;


    public void requestPermission(final Activity activity, final PermissionCallback callback, String... permissions) {
        mSetting = new PermissionSetting(activity);
        mRationale = new DefaultRationale();
        AndPermission.with(activity)
                .rationale(mRationale)
                .permission(permissions)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        LogUtil.log("获取"+arrToString(permissions)+"权限成功");
                        callback.onSuccess(permissions);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        LogUtil.log("获取"+arrToString(permissions)+"权限失败");
                        callback.onFail(permissions);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .start();
    }

    private String arrToString (List<String> arrStr) {
        StringBuffer sb = new StringBuffer();
        for(String str : arrStr) {
            sb.append(str+",");
        }
        return sb.toString();
    }



}
