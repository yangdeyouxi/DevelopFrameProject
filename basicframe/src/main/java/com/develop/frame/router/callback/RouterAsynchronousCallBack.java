package com.develop.frame.router.callback;

/**
 * Created by yangjiahuan on 2017/11/14.
 */

import java.util.List;

/**
 * 这个接口用于被路由的方法回传结果
 */
public interface RouterAsynchronousCallBack<T>{
    public void callBack(T result);

    public void callBack(List<T> results);
}