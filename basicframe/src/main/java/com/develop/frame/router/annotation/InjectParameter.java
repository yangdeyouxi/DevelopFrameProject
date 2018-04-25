package com.develop.frame.router.annotation;

/**
 * Created by yangjiahuan on 2017/11/13.
 * 用于注入参数
 */

public @interface InjectParameter {
    //参数传递是键值对形式，这里传入键，用于获取对应的值
    String key();

}
