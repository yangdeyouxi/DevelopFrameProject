package com.develop.frame.router;

import android.util.Log;

import com.develop.frame.router.api.Router;
import com.develop.frame.router.callback.RouterAsynchronousCallBack;
import com.develop.frame.router.callback.RouterMethodCallBack;
import com.develop.frame.router.callback.RouterSynchronizationCallBack;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.routertype.builder.BuilderAcivityRouter;
import com.develop.frame.router.routertype.builder.BuilderMethodRouter;
import com.develop.frame.router.routertype.executer.RouterExecuterMethod;

import java.util.HashMap;

/**
 * Created by yangjiahuan on 2017/12/1.
 */

public class DemoClass {

    //路由方法的示例
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public void routerRegistMethid(){
        //在这里将hello方法注册为开放的被调用的路由方法
        //具体的执行类是RouterExecuterMethod
        RouterExecuterMethod method = new RouterExecuterMethod();
        method.setRouterMethodCallBack(new RouterSynchronizationCallBack() {
            @Override
            public Object callBack(HashMap map, final RouterAsynchronousCallBack valueCallBack) {
                TestRouterMethod testRouterMethod = new TestRouterMethod();
                testRouterMethod.hello(new CallHello(){
                    @Override
                    public void callback(String value) {
                        valueCallBack.callBack(value);
                    }
                });
                return null;
            }
        });

        Router.getInstance().addRouter("getHello",null,method);
    }

    public void routerReceiveMethod(){
        BuilderMethodRouter builder = new BuilderMethodRouter();
        builder.buildMethodCall(new RouterMethodCallBack() {
            @Override
            public void call(RouterResult result) {
                Log.i("testtag","result:" + result.result);
            }
        });
        builder.buildParam("aaa","bbb");
        builder.buildTag("getHello");
        Router.getInstance().invoke(builder);
    }

     class TestRouterMethod {
        public void hello(final CallHello callHello){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    callHello.callback("2_second_later");
                }
            }).start();

        }
    }

    public interface CallHello {
        public void callback(String value);
    }



    //路由Activity的示例
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public void routerRegistActivity(){
        //        RouterExecuterActivityName activityName = new RouterExecuterActivityName();
        //        Router.getInstance().addRouter("second",SecondActivity.class,activityName);
    }

    public void routerReceiveActivity(){
        BuilderAcivityRouter router = new BuilderAcivityRouter();
        router.buildTag("second");
//        router.buildContext(this);
        router.buildParam("first","IAmFirst");
        RouterResult result = Router.getInstance().invoke(router);
    }

}
