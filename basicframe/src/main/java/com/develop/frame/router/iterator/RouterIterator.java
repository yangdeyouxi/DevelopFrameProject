package com.develop.frame.router.iterator;


import com.develop.frame.router.routertype.builder.Builder;

/**
 * Created by yangjiahuan on 2017/11/1.
 * 路由拦截器，用于各种路由之前的拦截
 *
 */

public interface RouterIterator {
    /**
     * 当开始路由当时候，可以添加一些操作，以及控制是否要继续路由
     * @param builder  路由的参数
     * @return  是否要继续路由
     */
    public boolean onRouter(Builder builder);

}
