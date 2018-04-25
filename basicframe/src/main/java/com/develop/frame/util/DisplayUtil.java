package com.develop.frame.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by sam on 2017/11/28.
 *
 * 显示相关
 */

public class DisplayUtil {

    private DisplayUtil(){
        throw new UnsupportedOperationException("u can't instantiate me ...");
    }


    /**
     *
     * @param dpValue
     * @return  px值
     */
    public static int dp2px(final float dpValue){
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;

        return (int) (dpValue*scale+0.5f);
    }


    /**
     *
     * @param pxValue
     * @return dpValue
     */
    public static int px2dp(final float pxValue){
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale +0.5f);
    }


    /**
     *
     * @param spValue
     * @return px值
     */
    public static int sp2px(final float spValue){
        final float fontScale= Utils.getApp().getResources().getDisplayMetrics().scaledDensity;

        return (int)(spValue*fontScale +0.5f);
    }

    /**
     *
     * @param pxValue
     * @return spValue
     */

    public static int px2sp(final float pxValue){
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale + 0.5f);
    }

    /**
     *
     * @return 返回屏幕高度 pxValue
     */

    public static int getScreenHeightPx(){
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getScreenWidthPx(){
        WindowManager wm = (WindowManager) Utils.getApp()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    /**
     * 测量视图尺寸
     * @param view
     * @return  arr[0] 宽度, arr[1] 高度
     */
    private static int[] measureView (final View view){
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null){
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int widthSpec = ViewGroup.getChildMeasureSpec(0,0,lp.width);
        int lpHeight = lp.height;

        int heightSpec;
        if (lpHeight >0){
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,View.MeasureSpec.EXACTLY);
        }else{
            heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec,heightSpec);
        return new int[]{view.getMeasuredWidth(),view.getMeasuredHeight()};

    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     * @return 视图宽度
     */
    public static int getMeasureWidth(final View view){
        return measureView(view)[0];
    }

    /**
     * 获取测量视图高度
     *
     * @param view 视图
     * @return 视图高度
     */
    public static int getMeasureHeight(final View view){
        return measureView(view)[1];
    }


}
