package com.develop.frame.router.routertype.executer;


import com.develop.frame.router.callback.RouterSynchronizationCallBack;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.model.RouterResultConsts;
import com.develop.frame.router.routertype.builder.Builder;
import com.develop.frame.router.routertype.builder.BuilderMethodRouter;

/**
 * Created by yangjiahuan on 2017/11/2.
 * 真正执行路由的类，实现它的类与进行实际的路由跳转或者调用
 * 可以自定义跳转的方式
 */

public abstract class RouterExecuter {

    /**
     * 异步返回结果的变量
     */
    public RouterSynchronizationCallBack routerValueCallBack;

    /**
     * 提供路由服务的一方设置异步返回结果的回调
     * @param callback
     */
    public void setRouterMethodCallBack(RouterSynchronizationCallBack callback){
        routerValueCallBack = callback;
    }

    /**
     * 异步执行路由之前的检查
     * @param builder
     * @return
     */
    public RouterResult invokeAsynchronousBefore(Builder builder){
        RouterResult result = new RouterResult();
        if(!checkAsynchronous(builder,result)){
            return result;
        }
        return invokeAsynchronous(builder);
    }

    /**
     * 检查传入的异步执行参数是否完整
     * @param builder
     * @param result
     * @return
     */
    private boolean checkAsynchronous(Builder builder,RouterResult result){
        //执行进这个方法的话，需要保证异步返回结果的接口不为空
        BuilderMethodRouter builderMethodRouter = (BuilderMethodRouter)builder;
        if(null == builderMethodRouter.methodCall){
            result.resultCode = RouterResult.ROUTER_PARAMETER_ERROR;
            result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_PARAMETER_ERROR;
            return false;
        }
        return true;
    }

    /**
     * 执行同步路由之前的检查
     * @param builder
     * @return
     */
    public RouterResult invokeBefore(Builder builder){

        return invoke(builder);
    }


    /**
     * 异步的路由方法
     * @param builder
     * @return 这个值会返回是否调用成功
     */
    public abstract RouterResult invokeAsynchronous(Builder builder);

    /**
     * 同步的路由方法
     * @param builder
     * @return
     */
    public abstract RouterResult invoke(Builder builder);





}
