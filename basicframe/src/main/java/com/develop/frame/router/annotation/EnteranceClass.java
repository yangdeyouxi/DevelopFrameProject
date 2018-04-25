package com.develop.frame.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangjiahuan on 2017/10/31.
 * 可以被路由的入口注册，用来修饰模块的入口类，被注册的可以被跨模块调用
 * 一般情况下是Activity或者fragment使用
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnteranceClass {
    //这里的String用来填入这个类的路径
    String enterancePath();

}
