package com.develop.frame.router.routertype.executer;

import com.develop.frame.router.callback.RouterAsynchronousCallBack;
import com.develop.frame.router.callback.RouterMethodCallBack;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.routertype.builder.Builder;
import com.develop.frame.router.routertype.builder.BuilderMethodRouter;

import java.util.List;


/**
 * Created by yangjiahuan on 2017/11/11.
 */

public class RouterExecuterMethod extends RouterExecuter {

    /**
     * 异步路由的方法
     * @param builder
     * @return
     */
    @Override
    public RouterResult invokeAsynchronous(Builder builder) {
        final RouterResult result = new RouterResult();
        final BuilderMethodRouter builderMethodRouter = (BuilderMethodRouter)builder;
        try {//调用路由方法可能报错
            result.result = routerValueCallBack.callBack(builder.params, new RouterAsynchronousCallBack() {
                @Override
                public void callBack(Object resultValue) {//在这个回调里面处理异步的路由结果
                    //这里接收异步的结果
                    result.result = resultValue;
                    //顺利路由了
                    RouterMethodCallBack methodCallBack = builderMethodRouter.methodCall;
                        //异步回调
                    methodCallBack.call(result);
                }

                @Override
                public void callBack(List results) {
                    //这里接收异步的结果
                    result.resultList = results;
                    //顺利路由了
                    RouterMethodCallBack methodCallBack = builderMethodRouter.methodCall;
                    //异步回调
                    methodCallBack.call(result);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            result.resultCode = RouterResult.ROUTER_CALL_ERROR;
            result.errorDescription = e.toString();
            return result;
        }
        return result;
    }

    /**
     * 同步路由的方法
     * @param builder
     * @return
     */
    @Override
    public RouterResult invoke(Builder builder) {
        RouterResult result = new RouterResult();
        BuilderMethodRouter builderMethodRouter = (BuilderMethodRouter)builder;
        Object data = null;
        try {//调用路由方法可能报错
            data = routerValueCallBack.callBack(builderMethodRouter.params,null);
        }catch (Exception e){
            e.printStackTrace();
            result.resultCode = RouterResult.ROUTER_CALL_ERROR;
            result.errorDescription = e.toString();
            return result;
        }
        result.result = data;
        return result;
    }





}
