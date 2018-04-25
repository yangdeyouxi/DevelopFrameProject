package com.develop.frame.router.callback;

import java.util.HashMap;

/**
 * Created by yangjiahuan on 2017/11/14.
 */

public interface RouterSynchronizationCallBack<T> {

    //这里的这个方法用于路由之后返回结果
    public T callBack(HashMap map, RouterAsynchronousCallBack valueCallBack);

}
