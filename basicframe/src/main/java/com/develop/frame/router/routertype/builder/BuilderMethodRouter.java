package com.develop.frame.router.routertype.builder;


import com.develop.frame.router.routertype.executer.RouterExecuter;
import com.develop.frame.router.routertype.executer.RouterExecuterMethod;

/**
 * Created by yangjiahuan on 2017/11/11.
 * 用于路由方法,这里直接通过一个接口来执行真正需要执行的方法，直接交给提供服务的人来写好调用的逻辑
 * 使用服务的人只需要传入所需要的参数即可
 */

public class BuilderMethodRouter extends Builder{


    @Override
    public RouterExecuter getRouterExecuter() {
        return new RouterExecuterMethod();
    }


}
