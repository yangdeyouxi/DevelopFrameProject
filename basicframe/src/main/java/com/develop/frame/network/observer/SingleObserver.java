package com.develop.frame.network.observer;

import com.develop.frame.bases.BasePresenter;
import com.develop.frame.network.bean.ErrorBean;
import com.develop.frame.network.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yangjh on 2018/4/30.
 */

public abstract class SingleObserver<T> implements Observer<T> {

    BasePresenter mBasePresenter;

    public SingleObserver(BasePresenter basePresenter) {
        mBasePresenter = basePresenter;
    }

    public SingleObserver() {

    }

    /**
     * 失败回调
     *
     * @param errorBean
     */
    protected abstract void onFail(ErrorBean errorBean);

    /**
     * 成功回调
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 开始调用
     *
     * @param d
     */
    protected void onStart(Disposable d) {
        if (null != mBasePresenter) {
            mBasePresenter.addDisposable(d);
        }
    }

    /**
     * 调用结束
     */
//    protected abstract void onFinished();
    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    @Override
    public void onNext(T baseResponse) {
//        if (baseResponse.getSuccess()) {
//            onSuccess(baseResponse.getData());
//        } else {
//            ErrorBean errorBean = new ErrorBean(baseResponse.getMessage());
//            errorBean.setErrorCode(-1);
//            onFail(errorBean);
//        }
        onSuccess(baseResponse);
    }

    @Override
    public void onError(Throwable e) {
        ErrorBean error = ApiException.handleException(e).getErrorBean();
        onFail(error);
    }

    @Override
    public void onComplete() {
//        onFinished();
    }

}
