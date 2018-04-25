package com.develop.frame.network.transformer;


import android.app.Dialog;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Allen on 2016/12/20.
 * <p>
 *
 * @author Allen
 *         控制操作线程的辅助类
 */

public class Transformer {

    /**
     * 无参数
     *
     * @param <T> 泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return upstream -> upstream
//                .map(data->data.data)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
        //备注原型
//    return new Transformer<T, T>() {
//        @Override
//        public Observable<T> call(Observable<T> upstream) {
//            return upstream.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        }
//    };

    /**
     * 带参数  显示loading对话框
     *
     * @param dialog loading
     * @param <T>    泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(final Dialog dialog) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (dialog != null) {
                        dialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
