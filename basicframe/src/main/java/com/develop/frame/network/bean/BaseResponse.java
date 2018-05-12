package com.develop.frame.network.bean;

/**
 * Created by sam on 2017/12/7.
 *
 * {
 *     data:{ T
 *
 *     }
 *     success: true/ false
 * }
 * 返回值的bean定义，这里需要和服务器协商好
 */

public class BaseResponse<T> {
    private T data;
    private boolean success;
    public String message;
    public T getData(){
        return data;
    }
    public boolean getSuccess(){
        return this.success;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }


    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setData(T data){
        this.data  = data;
    }
    @Override
    public String toString(){
        return "BaseData{+data="+data+ "success="+(success?"true":"false")+"}";

    }
}
