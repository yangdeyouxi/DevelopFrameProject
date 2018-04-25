package com.develop.frame.router.api;

import android.app.Activity;

import com.develop.frame.router.iterator.RouterIterator;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.routertype.builder.Builder;
import com.develop.frame.router.routertype.executer.RouterExecuter;
import com.develop.frame.router.routertype.executer.RouterExecuterActivityName;


/**
 * Created by yangjiahuan on 2017/10/31.
 */

public class Router {

    /**
     * 注册可以被路由的类
     * @param path
     * @param classObj
     * @param routerExecuter
     */
    public void addRouter(String path, Class classObj, RouterExecuter routerExecuter){
        mRouterInternel.addRouter(path,classObj,routerExecuter);
    }

    /**
     * 删除注册好的路由
     * @param path
     */
    public void removeRouter(String path){
        mRouterInternel.removeRouter(path);
    }


    /**
     * 注册Activity的路由配置
     * @param path
     * @param classObj
     */
    public void addActivityRouter(String path, Class classObj){
        if(classObj.isAssignableFrom(Activity.class)) {
            mRouterInternel.addRouter(path, classObj, new RouterExecuterActivityName());
        }
    }

    /**
     * 执行路由
     * @param builder
     * @return
     */
    public RouterResult invoke(Builder builder){
        builder.buildRouter(this);
        return mRouterInternel.invoke(builder);
    }

    /**
     * 添加拦截器
     */
    public void addRouterIterator(RouterIterator iterator){
        mRouterInternel.addIterator(iterator);
    }

    /**
     * 移除拦截器
     * @param iterator
     */
    public void removeIterator(RouterIterator iterator){
        mRouterInternel.removeIterator(iterator);
    }

    /**
     * 注入参数
     */
    public void inJection(Object injectObj){

    }


    private RouterInternel mRouterInternel;

    private Router(){
        mRouterInternel = new RouterInternel();
    }
    public static Router getInstance(){
        if(null == mInstance){
            synchronized (Router.class){
                if(null == mInstance){
                    mInstance = new Router();
                }
            }
        }
        return mInstance;
    }
    private static volatile Router mInstance;

}
