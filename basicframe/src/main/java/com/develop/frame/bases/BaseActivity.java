package com.develop.frame.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.develop.frame.util.LanguageUtil;
import com.orhanobut.logger.Logger;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sam on 2018/3/23.
 */

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    protected P mPresenter;

    public Context mContext;

    private Unbinder mUnBinder = null;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale locale = LanguageUtil.getLanguageType();
        super.attachBaseContext(BixyContextWrapper.wrap(newBase, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = initPresenter();
        initView();
        initData();
        Logger.t(TAG).d("on Create");
    }


    @Override
    protected  void onStop(){
        super.onStop();
        Logger.t(TAG).d("onStop()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Logger.t(TAG).d("onResume()");
    }

    @Override
    protected void onDestroy(){
        if (mPresenter!=null){
            mPresenter.detach();
            mPresenter.doDispose();
            mPresenter=null;
        }
        if (mUnBinder!=null){
            mUnBinder.unbind();
            mUnBinder = null;
        }
        Logger.t(TAG).d("onDestroy()");
        super.onDestroy();
    }

    protected  abstract int getLayoutId();

    protected abstract  void initView();
    protected abstract  void initData();
    protected abstract P initPresenter();
}
