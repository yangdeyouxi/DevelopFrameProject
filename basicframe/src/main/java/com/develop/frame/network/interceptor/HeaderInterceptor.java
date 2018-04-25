package com.develop.frame.network.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sam on 2017/12/7.
 */

public class HeaderInterceptor implements Interceptor{

    private Map<String, Object> headerMaps = new TreeMap<>();

    public HeaderInterceptor(Map<String,Object> headerMaps){
        this.headerMaps = headerMaps;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder request = chain.request().newBuilder();
        Log.d("HeaderInterceptor",""+headerMaps.size());
        if (headerMaps !=null && headerMaps.size()>0){
            for (Map.Entry<String,Object> entry:headerMaps.entrySet()){
                request.addHeader(entry.getKey(),(String)entry.getValue());
                Log.d("addHeader",entry.getKey() +"    " +entry.getValue());
            }
        }
        return chain.proceed(request.build());
    }
}
