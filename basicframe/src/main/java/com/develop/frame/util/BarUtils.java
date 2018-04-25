package com.develop.frame.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

/**
 * Created by sam on 2018/3/23.
 */

public final class BarUtils {
    private static final int DEFAULT_ALPHA= 112;
    private static final String  TAG_COLOR="TAG_COLOR";
    private static final String TAG_ALPHA  = "TAG_ALPHA";
    private static final int TAG_OFFSET = -123;

    private BarUtils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 获取状态栏高度(单位：px)
     *
     *  @return 状态栏高度(单位:px)
     */
    public static int getStatusBarHeight(){
        Resources resources = Utils.getApp().getResources();
        int resourceId  = resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 为View 增加MarginTop为状态栏高度
     * @param view
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view){
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset !=null &&(Boolean)haveSetOffset) return;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)
                view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin+getStatusBarHeight(),layoutParams.rightMargin,layoutParams.bottomMargin);
        view.setTag(TAG_OFFSET,true);
    }

    /**
     * 为 View 减少MarginTop 状态栏高度
     * @param view
     */

    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view){
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset !=null &&!(Boolean) haveSetOffset) return;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)
                view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin -getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);

        view.setTag(TAG_OFFSET,false);
    }

    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color){
        setStatusBarColor(activity,color,DEFAULT_ALPHA,false);
    }
    /**
     * 设置状态栏颜色
     * @param activity
     * @param color   颜色值
     * @param alpha   透明度
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from =0,to=255) final  int alpha){
        setStatusBarColor(activity,color,alpha,false);
    }

    /**
     * 设置状态栏颜色
     * @param activity
     * @param color
     * @param alpha
     * @param isDecor   {@code true}: 设置在DecorView中 <br>
     *                  {@code false}: 设置在ContentView 中
     */


    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from=0,to=255)final int alpha,
                                         final boolean isDecor){
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity,color,alpha,isDecor);
    }



    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color){
        setStatusBarColor(fakeStatusBar,color,DEFAULT_ALPHA);
    }

    /**
     * 设置状态栏颜色
     * @param fakeStatusBar
     * @param color
     * @param alpha   状态栏透明度，此透明度非颜色中的透明度
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,@ColorInt final int color,
                                         @IntRange(from =0,to =255)final int alpha){

        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;

        fakeStatusBar.setVisibility(View.VISIBLE);

        transparentStatusBar((Activity)fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color,alpha));


    }

    /**为DrawerLayout 设置状态栏颜色
     *
     * @param activity
     * @param drawer
     * @param fakeStatusBar
     * @param color
     * @param isTop
     */

    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final  int color,
                                                final boolean isTop){
        setStatusBarColor4Drawer(activity,drawer,fakeStatusBar,color,DEFAULT_ALPHA,isTop);
    }

    /**
     *  为DrawerLayout 设置状态栏颜色
     * @param activity
     * @param drawer
     * @param fakeStatusBar
     * @param color
     * @param alpha
     * @param isTop
     */

    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,@NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                @IntRange(from=0,to=255)final  int alpha,
                                                final boolean isTop){

        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;

        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarColor(fakeStatusBar,color,isTop?alpha:0);

        int len = drawer.getChildCount();
        for (int i =0 ;i<len;i++){
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop){
            hideAlphaView(activity);
        }else{
            addStatusBarAlpha(activity,alpha,false);
        }

    }

    private static void addStatusBarColor(final Activity activity,final int color,final int alpha,boolean isDecor){
        ViewGroup parent = isDecor?
                (ViewGroup)activity.getWindow().getDecorView():
                (ViewGroup)activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView !=null){
            if (fakeStatusBarView.getVisibility() == View.GONE){
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(getStatusBarColor(color,alpha));
        }else{
            parent.addView(createColorStatusBarView(parent.getContext(),color,alpha));
        }
    }


    private static int getStatusBarColor(final int color,final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;

        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);

        return Color.argb(255,red,green,blue);
    }

    /**
     * 绘制一个和状态栏一样高的颜色矩形
     * @param context,
     * @param color
     * @param alpha
     */

    private static View createColorStatusBarView(final Context context,
                                                 final int color,
                                                 final int alpha){

        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight()));
        statusBarView.setBackgroundColor(getStatusBarColor(color,alpha));
        statusBarView.setTag(TAG_COLOR);
        return statusBarView;
    }



    private static void transparentStatusBar(final Activity activity){
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void hideAlphaView(final Activity activity){
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView ==null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }


    /**
     * 设置状态栏透明度
     * @param activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity){
        setStatusBarAlpha(activity,DEFAULT_ALPHA,false);
    }

    /**
     * 设置状态栏透明度
     * @param activity
     * @param alpha
     */

    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from=0,to=255)final int alpha){
        setStatusBarAlpha(activity,alpha,false);
    }

    /**
     * 设置状态栏透明度
     * @param activity
     * @param alpha
     * @param isDecor{@code true}: 设置在DecorView中 <br>
     *                  {@code false}: 设置在CotentView 中
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from=0,to=255)final int alpha,
                                         final boolean isDecor){
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity,alpha,isDecor);
    }


    /**
     * 设轩状态栏透明度
     * @param fakeStatusBar
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar){
        setStatusBarAlpha(fakeStatusBar,DEFAULT_ALPHA);
    }

    /**
     * 设置状态栏透明度
     * @param fakeStatusBar
     * @param alpha   状态栏透明度
     */

    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar,
                                         @IntRange(from =0,to=255)final int alpha){
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity)fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha,0,0,0));
    }


    /**
     * 为 DrawerLayout 设置状态栏透明度
     * @param activity
     * @param drawer
     * @param fakeStatusBar
     * @param isTop
     */

    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                final boolean isTop){
        setStatusBarAlpha4Drawer(activity,drawer,fakeStatusBar,DEFAULT_ALPHA,isTop);

    }

    /**
     * 为 DrawerLayout 设置状态栏透明度
     * @param activity
     * @param drawer
     * @param fakeStatusBar
     * @param isTop
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @IntRange(from=0,to=255)final int alpha,
                                                final  boolean isTop){
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarAlpha(fakeStatusBar,isTop?alpha:0);
        int len = drawer.getChildCount();
        for (int i = 0; i<len;i++){
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop){
            hideAlphaView(activity);
        }else{
            addStatusBarAlpha(activity,alpha,false);
        }

    }


    private static void hideColorView(final Activity activity){
        ViewGroup decorView = (ViewGroup)activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void addStatusBarAlpha(final Activity activity,
                                          final int alpha,
                                          boolean isDecor){
        ViewGroup parent = isDecor?
                (ViewGroup)activity.getWindow().getDecorView():
                (ViewGroup)activity.getWindow().findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_ALPHA);

        if (fakeStatusBarView !=null){
            if (fakeStatusBarView.getVisibility() == View.GONE){
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(alpha,0,0,0));
        }else{
            parent.addView(createAlphaStatusBarView(parent.getContext(),alpha));
        }
    }

    private static View createAlphaStatusBarView(final Context context,
                                                 final int alpha){
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight()));
        statusBarView.setBackgroundColor(Color.argb(alpha,0,0,0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }




    /*************************
     * Action Bar
     * *********************/
    /**
     * @param activity
     * @return
     */
    public static int getActionBarHeight(@NonNull final Activity activity){
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize,tv,true)){
            return TypedValue.complexToDimensionPixelSize(tv.data,activity.getResources().getDisplayMetrics());
        }
        return 0;
    }


    ///////////////
    /// Notification bar
    ////////////////////
    /**
     * 显示通知栏
     * @param context
     * @param isSettingPanel
     *
     * <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
     */

    public static void showNotificationBar(@NonNull final Context context,
                                           final boolean isSettingPanel){
        String methodName = (Build.VERSION.SDK_INT <=16)?"expand":
                (isSettingPanel?"expandSettingsPanel":"expandNotificationsPanel");

        invokePanels(context,methodName);
    }


    /**
     * 隐藏通知栏
     * @param context
     *
     * <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
     */
    public static void hideNotificationBar(@NonNull final Context context){
        String methodName = (Build.VERSION.SDK_INT <=16)?"collapse":"collapsePanels";
        invokePanels(context,methodName);
    }


    private static void invokePanels(@NonNull final Context context,final String methodName){
        try{
            @SuppressLint("WrongConstant") Object service = context.getSystemService("statusbar");

            Class<?>statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /////////////
    ///Navigation bar
    ////////////////

    /**
     * 获取导航栏高度
     * @return   0 ，代表不存在
     */
    public static int getNavBarHeight() {
        Resources res = Utils.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 隐藏导航栏
     * @param activity
     */

    public static void hideNavBar(@NonNull final Activity activity){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN) return;
        if (getNavBarHeight()>0) {
            View decorView = activity.getWindow().getDecorView();

            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            decorView.setSystemUiVisibility(uiOptions);
        }
    }


}
