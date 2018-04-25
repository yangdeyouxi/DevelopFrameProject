package com.develop.frame.network.bean;

/**
 * Created by sam on 2018/2/1.
 *
 * 所有 success = false 时 用API Exception 处理成ErrorBean.
 */

public class ErrorBean {
    private int errorCode;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(int errorCode){this.errorCode= errorCode;}

    public int getErrorCode(){return errorCode;}

    public ErrorBean(String message){
        this.message = message;
    }
}
