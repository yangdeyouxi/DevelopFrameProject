package com.develop.frame.databind.bind;

import com.develop.frame.databind.data.DataLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjiahuan on 2018/1/22.
 * 这个类用于数据改变时的反向绑定
 */

public abstract class BindModel {
    //因为这个Observer时一个很容易引起问题的对象，所以需要使用弱引用来实现,WeakReference中的值不在其他地方被引用，并在创建他的地方被GC之后，会被回收
    private List mObservers;
    private String bindId = null;

    /**
     * 触发保存对象的点，当这个方法被调用时，这个对象会被保存进
     * 全局统一的缓存处
     * @param id
     */
    public void setBindId(String id){
        bindId = id;
        DataLoader.getInstance().saveObject(this);
    }

    public String getBindId(){
        return bindId;
    }

    /**
     * 监听数据改变，这个用于监听游戏内的属性
     * 当调用这个方法时，这条数据会被同步
     * @param observer
     */
    public <T extends BindModel> T registObserve(Observer<T> observer){
        T data = (T)DataLoader.getInstance().saveObject(this);
        data.setObserver(observer);
        return data;
    }

    /**
     * 移除注册
     * @param observer
     */
    public void removeObserve(Observer observer){
        try {
            if(null == mObservers)return;
            if (mObservers.contains(observer)) {
                mObservers.remove(observer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void setObserver(Observer observer){
        if(null == mObservers){
            mObservers = new ArrayList<>();
        }
        WeakReference weakReference = new WeakReference(observer);
        mObservers.add(weakReference);
    }

    /**
     * 当属性变化需要通知其他持有当前对象的地方时调用
     */
    public <T> void valueChanged(){
        if(null == mObservers)return;
        int size = mObservers.size();
        WeakReference weakReference = null;
        for(int i = size-1;i >= 0;i--){
            weakReference = (WeakReference)mObservers.get(i);
            Observer observer = (Observer)weakReference.get();
            if(null == observer){
                mObservers.remove(weakReference);
                continue;
            }
            observer.onChanged((T)this);
        }
    }

    /**
     * 通知改变的接口
     */
    public interface Observer<T>{
        public void onChanged(T object);
    }

}
