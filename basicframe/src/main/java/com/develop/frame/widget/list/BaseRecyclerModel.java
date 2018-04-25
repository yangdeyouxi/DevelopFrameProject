package com.develop.frame.widget.list;


import com.develop.frame.databind.bind.BindModel;

import java.lang.reflect.ParameterizedType;

/**
 * Created by yangjh on 2017/1/6.
 * 这个类用来适配BaseRecyclerView
 * 注意：继承这个类的对象，在构造方法中一定要调用到BaseRecyclerModel的构造器
 *  可以在构造器调用super()方法
 */

public abstract class BaseRecyclerModel<T extends BaseRecyclerItemView> extends BindModel {

    public BaseRecyclerModel(){
        //注册自己的ViewType
        BaseRecyclerPresenter.getInstance().registModel(this);
        //绑定BaseRecyclerView
        BaseRecyclerPresenter.getInstance().bindModelView(this,bindViewClass());
    }

    public int getViewType() {
        return BaseRecyclerPresenter.getInstance().getModelType(this);
    }

    /**
     * 绑定用于在Adapter中显示的View
     * @return
     */
    public Class<T> bindViewClass(){
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
//        return (Class) params[0];
    }

    /**
     * 获取当前Model的ID
     * @return
     */
    public abstract String getId();


}
