package com.develop.frame.router.api;

import com.develop.frame.router.annotation.InjectParameter;
import com.develop.frame.router.iterator.RouterIterator;
import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.model.RouterResultConsts;
import com.develop.frame.router.routertype.builder.Builder;
import com.develop.frame.router.routertype.executer.RouterExecuter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by yangjiahuan on 2017/11/1.
 * 用来真正执行路由的对象
 */

public class RouterInternel {
    //键为类的别名，值为一种类型的路由的执行类
    private HashMap<String,RouterExecuter> routerMaps = new HashMap<String, RouterExecuter>();
    //键为类的别名，值为类
    private HashMap<String,String> classMaps = new HashMap<String, String>();
    //用于拦截的拦截器集合
    private ArrayList<RouterIterator> iterators = new ArrayList<RouterIterator>();

    /**
     * 添加路由
     * @param tag
     * @param className
     * @param routerExecuter
     */
    public void addRouter(String tag,Class className,RouterExecuter routerExecuter){
        routerMaps.put(tag,routerExecuter);
        classMaps.put(tag,(null == className) ? null : className.getName());
    }

    /**
     * 删除路由
     * @param tag
     */
    public void removeRouter(String tag){
        routerMaps.remove(tag);
        classMaps.remove(tag);
    }

    /**
     * 添加拦截器
     * @param iterator
     */
    public void addIterator(RouterIterator iterator){
        iterators.add(iterator);
    }

    /**
     * 删除拦截器
     * @param iterator
     */
    public void removeIterator(RouterIterator iterator){
        iterators.remove(iterator);
    }

    /**
     * 执行路由
     * 这里会首先拦截过滤一遍
     */
    public RouterResult invoke(Builder builder){
        //进行拦截器的拦截
        for(RouterIterator iterator : iterators){
            if(iterator.onRouter(builder)){
                RouterResult result = new RouterResult();
                result.resultCode = RouterResult.ROUTER_CALL_ITERATORED;
                result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_CALL_ITERATORD;
                return result;
            }
        }
        RouterExecuter routerExecuter = routerMaps.get(builder.routerTag);
        builder.routerPath = classMaps.get(builder.routerTag);//将原本的代号路径切换为真实的类的路径
        //首先检查是否是异步的
        if(null != builder.methodCall){//发现是异步的
            return routerExecuter.invokeAsynchronousBefore(builder);
        }
        //同步的路由方法
        return routerExecuter.invoke(builder);
    }


    /**
     * 对指定的类中的值进行赋值
     * @param injectObj
     * @param params
     */
    public void inject(Object injectObj,HashMap<String,Object> params){
        Field[] declaredFields = injectObj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            InjectParameter annotation = field.getAnnotation(InjectParameter.class);
            if (annotation == null)continue;//不需要注入值
                String parameter = annotation.key();
                Object value = params.get(parameter);
                if(null == value)continue;//没有对应的值则跳过
                field.setAccessible(true);//防止private修饰的变量无法赋值
                try {//进行赋值
                    field.set(injectObj, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

        }
    }





}
