package com.develop.frame.router.routertype.executer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.develop.frame.router.model.RouterResult;
import com.develop.frame.router.model.RouterResultConsts;
import com.develop.frame.router.routertype.builder.Builder;
import com.develop.frame.router.routertype.builder.BuilderAcivityRouter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yangjiahuan on 2017/11/2.
 * 根据Activity的完整类名的规则来进行跳转的实现
 */

public class RouterExecuterActivityName extends RouterExecuter{

    @Override
    public RouterResult invoke(Builder builder) {
        BuilderAcivityRouter buildera = (BuilderAcivityRouter)builder;
        return startActivity(buildera.context,buildera.routerPath,buildera.params,buildera.startForResult,buildera.requestCode);
    }

    /**
     * 打开Activity
     * @param context
     * @param activityName
     * @param maps
     * @param forResult
     * @param requestCode
     * @return
     */
    public RouterResult startActivity(Context context, String activityName, HashMap<String,Object> maps, boolean forResult, int requestCode){
        RouterResult result = new RouterResult();

        if(!checkInput(context,activityName,maps,forResult,requestCode,result)){
            return result;
        }
        //数据转换
        Bundle bundle = createBundle(maps);

        Class targetActivity = null;
        try {
            targetActivity = Class.forName(activityName);
        }catch (Exception e){e.printStackTrace();}

        try {
            Intent intent = new Intent(context, targetActivity);
            intent.putExtra(RouterResultConsts.RouterData.ROUTER_ACTIVITY_BUNDLE, bundle);
            if(!forResult){
                context.startActivity(intent);
            }else {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.resultCode = RouterResult.ROUTER_CALL_ERROR;
            result.errorDescription = e.toString();
            return result;
        }
        return result;
    }

    private boolean checkInput(Context context, String activityName, HashMap<String,Object> map, boolean forResult, int requestCode,RouterResult result){
        //首先检查参数是否为空
        if(null == context){
            result.resultCode = RouterResult.ROUTER_PARAMETER_ERROR;
            result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_CONTEXT_NULL;
            return false;
        }
        if(TextUtils.isEmpty(activityName)){
            result.resultCode = RouterResult.ROUTER_PATH_ERROR;
            result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_PATH_WRONG;
            return false;
        }
        try {
            Class targetActivity = Class.forName(activityName);
        }catch (Exception e){
            e.printStackTrace();
            result.resultCode = RouterResult.ROUTER_PATH_ERROR;
            result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_PATH_WRONG;
            return false;
        }
        if(forResult && requestCode == -1){
            result.resultCode = RouterResult.ROUTER_PARAMETER_ERROR;
            result.errorDescription = RouterResultConsts.RouterWrong.ROUTER_REUQEST_CODE_NULL;
            return false;
        }

        return true;
    }

    /**
     * hashmap转换为bundle
     * @param map
     * @return
     */
    protected Bundle createBundle(HashMap<String,Object> map){
        Bundle bundle = new Bundle();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof Integer){
                bundle.putInt(entry.getKey(),(int)entry.getValue());
            }
            if(entry.getValue() instanceof String){
                bundle.putString(entry.getKey(),(String) entry.getValue());
            }
            if(entry.getValue() instanceof Character){
                bundle.putChar(entry.getKey(),(Character) entry.getValue());
            }
            if(entry.getValue() instanceof Short){
                bundle.putShort(entry.getKey(),(Short) entry.getValue());
            }
            if(entry.getValue() instanceof Long){
                bundle.putLong(entry.getKey(),(Long) entry.getValue());
            }
            if(entry.getValue() instanceof Double){
                bundle.putDouble(entry.getKey(),(Double) entry.getValue());
            }
            if(entry.getValue() instanceof Float){
                bundle.putFloat(entry.getKey(),(Float) entry.getValue());
            }
            if(entry.getValue() instanceof Boolean){
                bundle.putBoolean(entry.getKey(),(Boolean) entry.getValue());
            }
            if(entry.getValue() instanceof Byte){
                bundle.putByte(entry.getKey(),(Byte) entry.getValue());
            }
            if(entry.getValue() instanceof ArrayList){
                bundle.putByte(entry.getKey(),(Byte) entry.getValue());
                ArrayList list = (ArrayList) entry.getValue();
                //集合序列化的转换
                if(list.size() > 0 && list.get(0) instanceof Parcelable){
                    bundle.putParcelableArrayList(entry.getKey(),(ArrayList) entry.getValue());
                }
            }
            if(entry.getValue() instanceof Parcelable){
                bundle.putParcelable(entry.getKey(),(Parcelable) entry.getValue());
            }
            if(entry.getValue() instanceof Serializable){
                bundle.putSerializable(entry.getKey(),(Serializable) entry.getValue());
            }
        }
        return bundle;
    }

    @Override
    public RouterResult invokeAsynchronous(Builder builder) {
        return null;
    }
}
