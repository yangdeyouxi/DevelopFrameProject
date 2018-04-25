package com.develop.frame.bases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.develop.frame.base.BaseViewInterface;
import com.develop.frame.databind.bind.BindModel;
import com.develop.frame.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zengh on 2018/2/1.
 */

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements BaseViewInterface {

    public Activity mActivity;
    public Context mContext;

    private LoadingDialog mLoadingDialog;


    public Unbinder bind;
    public P iBasePresenter;

    public View mRootView = null;

    //用于页面的数据通知时的回调，由于BindModel中的引用是弱引用，所以Observer需要在其他地方有强引用，这样才可以避免Observer的生命周期在方法内，一下子就被回收导致无法被通知
    private List<BindModel.Observer> observers = new ArrayList<BindModel.Observer>();

    /**
     * 用于添加数据变化的监听
     * @param model
     * @param observer
     */
    public void addObserver(BindModel model,BindModel.Observer observer){
        observers.add(observer);
        model.registObserve(observer);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null != mRootView)return mRootView;
        if (getLayoutId() != 0) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            bind = ButterKnife.bind(this, mRootView);
            iBasePresenter = initPresenter();
        }else {
            throw new IllegalStateException("没有找到layoutId，请确认是否添加layoutId");
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParams();
        createLoading();
        beforeInitView(savedInstanceState);
        initView();
        initEventClick();
        initData();
    }



    public void initParams() {
        mActivity = getActivity();
        mContext = getContext();
    }

    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }

    }

    public void showLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void createLoading() {
        mLoadingDialog = new LoadingDialog(mActivity);

    }

    //打开或关闭键盘
    protected void hintKb(boolean show) {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            View currentFocus = mActivity.getCurrentFocus();
            if (currentFocus != null) {
                IBinder windowToken = currentFocus.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        if (iBasePresenter!=null){
            iBasePresenter.detach();
            iBasePresenter.doDispose();
            iBasePresenter=null;
        }
        hideLoadingDialog();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public abstract void initEventClick();

    protected abstract P initPresenter();
}
