package com.develop.frame.databind.data;

import android.support.v4.util.LruCache;
import com.develop.frame.databind.bind.BindModel;
import com.develop.frame.databind.util.CloneUtil;

import java.util.HashMap;

/**
 * Created by yangjiahuan on 2018/1/22.
 * 这个类用于管理全局的变量，目的是为了有一个数据管理中心+数据反向绑定来达到不需要EventBus的效果
 */

public class DataLoader {

    private static final int EVERY_TYPE_SAVE_COUNT = 1000;//每个类对象保存

    HashMap<String,LruCache> mAllData = new HashMap<String,LruCache>();

    /**
     * 保存值
     * @param object
     * @param <T>
     */
    public <T extends BindModel> T saveObject(T object){
        String name = object.getClass().getName();
        if(null == mAllData.get(name)){
            LruCache currentType = new LruCache(EVERY_TYPE_SAVE_COUNT);
            mAllData.put(name,currentType);
        }
        //这里在保存时需要检查是否已经有这个对象
        Object result = mAllData.get(name).get(object.getBindId());
        if(null != result && ((T)result).getBindId().equalsIgnoreCase(object.getBindId())){//如果已经有了，则将数据更新
            CloneUtil.clone(object,(T)result);
            object = (T)result;
            object.valueChanged();
            mAllData.get(name).put(object.getBindId(),object);//再放置一次
        }else{
            mAllData.get(name).put(object.getBindId(),object);
        }
        return object;
    }

    /**
     * 取出保存的对象
     * @param type
     * @param id
     * @param <T>
     * @return
     */
    public <T extends BindModel> T getObject(Class<T> type,String id){
        String name = type.getName();
        if(null == mAllData.get(name))return null;
        try {
            return (T) mAllData.get(name).get(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    private DataLoader(){
    }

    private static volatile DataLoader mInstance;

    public static DataLoader getInstance(){
        if(null == mInstance){
            synchronized (DataLoader.class){
                if(null == mInstance){
                    mInstance = new DataLoader();
                }
            }
        }
        return mInstance;
    }



}
