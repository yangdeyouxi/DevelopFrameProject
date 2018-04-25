package com.develop.frame.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by yangjiahuan on 2017/12/5.
 * 工具类，用于显示图片，以后升级库或者换库时没有风险
 */

public class ImageLoader {

    /**
     * 显示网络图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void showNetImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void showCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(new RequestOptions().circleCrop()).into(imageView);
    }

    public static void showCircleImage(Context context, int drawableId, ImageView imageView) {
        Glide.with(context).load(drawableId).apply(new RequestOptions().circleCrop()).into(imageView);
    }


}
