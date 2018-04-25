package com.develop.frame.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.develop.frame.R;


/**
 * Created by Zhang Qixiang on 2017/10/19.
 */

public class LoadingDialog extends Dialog {

    private ImageView mIvGulu;
    private Activity mActivity;
    private Animation mAnimation;

    public LoadingDialog(@NonNull Activity activity) {
        super(activity, R.style.dialog_transparent);
        mActivity = activity;
        initWindowParams();
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading);

        mIvGulu = findViewById(R.id.iv_gulu_rotate);
        mAnimation = AnimationUtils.loadAnimation(activity, R.anim.rotate_anim);


    }

    private void initWindowParams() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivity.finish();
    }

    @Override
    public void show() {
        try {
            if (mIvGulu != null) {
                mIvGulu.startAnimation(mAnimation);
            }
            super.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


