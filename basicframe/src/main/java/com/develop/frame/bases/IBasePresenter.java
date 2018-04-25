package com.develop.frame.bases;

import io.reactivex.disposables.Disposable;

/**
 * Created by sam on 2018/3/23.
 */

public interface IBasePresenter {

    //Activity关闭把view对象置空
    void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable disposable);

    //注销所有请求
    void doDispose();

}
