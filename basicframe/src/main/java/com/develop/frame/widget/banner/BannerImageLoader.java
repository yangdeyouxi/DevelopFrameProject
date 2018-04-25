package com.develop.frame.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by yangjiahuan on 2017/12/6.
 */

public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Object result = getLoadPath(path);
        Glide.with(context.getApplicationContext())
                .load(result)
                .into(imageView);
    }

    private Object getLoadPath( Object path){
        if(path instanceof String){
            return (String)path;
        }
        return null;
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView image = super.createImageView(context);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return image;
    }
}
