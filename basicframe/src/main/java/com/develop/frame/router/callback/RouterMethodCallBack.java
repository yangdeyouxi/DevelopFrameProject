package com.develop.frame.router.callback;

/**
 * Created by yangjiahuan on 2017/11/14.
 */


import com.develop.frame.router.model.RouterResult;

/**
 * 用于接收异步执行的方法的返回值
 * 调用路由方法的地方使用，用于接收异步路由的结果
 */
public interface RouterMethodCallBack{
    //这里的T是返回值
    public void call(RouterResult callback);
}
