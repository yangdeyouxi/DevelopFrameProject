package com.develop.frame.network.http;

import okhttp3.OkHttpClient;

/**
 * Created by sam on 2017/11/29.
 *
 * 封装OkHttp为单例模式
 */

public class HttpClient {

    private volatile static HttpClient instance=null;

    private OkHttpClient.Builder builder;


    public HttpClient(){
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance(){
        if (instance == null){
            synchronized (HttpClient.class){
                if (instance == null){
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public  OkHttpClient.Builder getBuilder(){
        return builder;
    }
}
