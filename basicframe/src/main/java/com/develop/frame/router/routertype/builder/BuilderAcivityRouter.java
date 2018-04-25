package com.develop.frame.router.routertype.builder;

import android.content.Context;

import com.develop.frame.router.model.RouterResultConsts;
import com.develop.frame.router.routertype.executer.RouterExecuter;
import com.develop.frame.router.routertype.executer.RouterExecuterActivityName;


/**
 * Created by yangjiahuan on 2017/11/2.
 */

public class BuilderAcivityRouter extends Builder {

        public Context context;
        public int requestCode = RouterResultConsts.RouterDefault.ACTIVITY_REQUEST_CODE;
        public boolean startForResult = false;

        public Builder buildContext(Context context){
            this.context = context;
            return this;
        }

        public Builder buildRequestCode(int requestCode){
            this.requestCode = requestCode;
            return this;
        }

        public Builder buildForResult(){
            startForResult = true;
            return this;
        }


    @Override
    public RouterExecuter getRouterExecuter() {
        return new RouterExecuterActivityName();
    }
}
