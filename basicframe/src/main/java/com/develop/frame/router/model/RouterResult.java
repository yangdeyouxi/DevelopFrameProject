package com.develop.frame.router.model;

import java.util.List;

/**
 * Created by yangjiahuan on 2017/10/31.
 * 用于返回路由之后的结果
 */

public class RouterResult<T> {

    public static final int ROUTER_SUCCESS = 0;//路由成功
    public static final int ROUTER_CALL_ERROR = -1;//路由失败报错，此时需要把报的错带回来，这时候错误就有很多了
    public static final int ROUTER_PATH_ERROR = -2;//所要路由的路径错误
    public static final int ROUTER_PARAMETER_ERROR = -3;//路由时传递的参数错误
    public static final int ROUTER_CALL_ITERATORED = -4;

    public int resultCode = 0;//请求结果码
    public String errorDescription;//错误描述
    public T result;//返回的结果
    public List<T> resultList;//返回的结果





}
