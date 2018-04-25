package com.develop.frame.network.observer;

import com.develop.frame.network.bean.BaseResponse;
import com.develop.frame.network.bean.ErrorBean;
import com.develop.frame.network.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by sam on 2017/12/7.
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
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
     * @param d
     */
    protected abstract void onStart(Disposable d);

    /**
     * 调用结束
     */
    protected abstract void onFinished();
    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseResponse.getSuccess()){
            onSuccess(baseResponse.getData());
        }else{
            ErrorBean errorBean = new ErrorBean(baseResponse.getMessage());
            errorBean.setErrorCode(-1);
            onFail(errorBean);
        }
    }
    @Override
    public void onError(Throwable e) {
        ErrorBean error = ApiException.handleException(e).getErrorBean();
        onFail(error);
    }

    @Override
    public void onComplete() {
        onFinished();
    }

}
