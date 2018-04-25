package com.develop.frame.widget.list;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by yangjh on 2017/1/6.
 * 主要用于BaseRecyclerView和BaseRecyclerModel的绑定
 * 以及BaseRecyclerView的实例化
 */

public class BaseRecyclerPresenter {

    private static volatile BaseRecyclerPresenter mInstance;

    private int mCurrrntIndex = 3;
    //用于注册model的viewType
    private HashMap<String,Integer> modelTypeBinder = new HashMap<String,Integer>();//用于注册model于viewType的关系
    private HashMap<Integer,Class> holderBinder = new HashMap<Integer,Class>();//用于注册viewType与view的关系

    private BaseRecyclerPresenter(){

    }

    public static BaseRecyclerPresenter getInstance(){
        if(null == mInstance){
            synchronized (BaseRecyclerPresenter.class){
                if(null == mInstance){
                    mInstance = new BaseRecyclerPresenter();
                }
            }
        }
        return mInstance;
    }

    /**
     * 注册model的viewType
     * 因为RecyclerView.Adapter的getItemViewType方法返回的是int类型的值
     * 我不想让每个model自己实现返回，容易出错，所以在这里写一个自增来实现
     * @param model
     */
    public void registModel(BaseRecyclerModel model){
        if(null != modelTypeBinder.get(model.getClass().getSimpleName()))return;
        modelTypeBinder.put(model.getClass().getSimpleName(),mCurrrntIndex);
        mCurrrntIndex++;
    }

    public int getModelType(BaseRecyclerModel model){
        if(null == modelTypeBinder.get(model.getClass().getSimpleName())){
            registModel(model);
        }
        return modelTypeBinder.get(model.getClass().getSimpleName());
    }

    /**
     * 绑定Model和View的关系
     * @param model
     * @param viewclass
     */
    public void bindModelView(BaseRecyclerModel model,Class viewclass){
        if(null != holderBinder.get(model.getViewType()))return;
        holderBinder.put(model.getViewType(),viewclass);
    }

    /**
     * 创建指定的BaseRecyclerView对象
     * @param context
     * @param viewType
     * @return
     */
    public BaseRecyclerItemView createView(Context context, int viewType){
        if(null == holderBinder.get(viewType))return null;
        //创建对象
        try {
            Class classType = holderBinder.get(viewType);
            Constructor<BaseRecyclerItemView> con = classType.getConstructor(Context.class);
            BaseRecyclerItemView obj = con.newInstance(context);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
