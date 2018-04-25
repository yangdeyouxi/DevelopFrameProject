package com.develop.frame.router.routertype.builder;

import com.develop.frame.router.api.Router;
import com.develop.frame.router.callback.RouterMethodCallBack;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.routertype.executer.RouterExecuter;

import java.util.HashMap;


/**
 * Created by yangjiahuan on 2017/11/2.
 */

public abstract class Builder {
    Router mRouter;
    public String routerTag;//路由的目标的代号标记
    public String routerPath;//路由的目标的实际跳转的路径，比如Activity的实际类的路径或者action、scheme等
    public HashMap<String,Object> params;
    public RouterMethodCallBack methodCall;//用于接收异步执行的路由的结果



    protected Builder(){}


    public Builder buildTag(String tag){
        routerTag = tag;
        return this;
    }

    public Builder buildParams(HashMap<String,Object> maps){
        //如果没有数据，就全部赋值
        if(null == params) {
            params = maps;
        }else {//如果已经有数据，就全部覆盖
            params.putAll(maps);
        }
        return this;
    }

    public Builder buildParam(String tag, Object obj){
        if(null == params){
            params = new HashMap<String,Object>();
        }
        params.put(tag,obj);
        return this;
    }

    public void buildRouter(Router router){
        mRouter = router;
    }

    /**
     * 用于接收异步执行的路由方法结果的接口
     * @param call
     */
    public void buildMethodCall(RouterMethodCallBack call){
        methodCall = call;
    }

    public RouterResult invoke() {
        return mRouter.invoke(this);
    }

    public abstract RouterExecuter getRouterExecuter();

}
