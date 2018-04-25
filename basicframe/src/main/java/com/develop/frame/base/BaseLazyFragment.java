package com.develop.frame.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.develop.frame.bases.BaseFragment;
import com.develop.frame.bases.IBasePresenter;
import com.orhanobut.logger.Logger;

/**
 * Created by sam on 2018/3/23.
 */

public abstract class BaseLazyFragment<P extends IBasePresenter> extends BaseFragment {

    //检测声明周期中是否己构建视图
    private  boolean isViewCreated = false;
    private boolean isUserVisible = false;
    private boolean isFirst = false;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        Logger.t(this.getClass().getSimpleName()).d("isVisibleToUser"+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        if (isUserVisible && isViewCreated){
            visibleToUser();
        }
    }
    protected void firstLoad(){
    }
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isUserVisible){
            visibleToUser();
        }
        Logger.t(this.getClass().getSimpleName()).d("onResume");
    }

}
