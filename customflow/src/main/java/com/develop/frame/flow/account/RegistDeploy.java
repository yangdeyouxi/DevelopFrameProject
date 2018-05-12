package com.develop.frame.flow.account;

import com.develop.frame.flow.base.BaseView;

/**
 * Created by yangjh on 2018/5/1.
 */

public interface RegistDeploy {

    public interface RegistView extends BaseView{

        /**
         * 获取注册的账号
         * @return
         */
        public String getRegistAcc();

        /**
         * 获取注册的密码
         * @return
         */
        public String getRegistPassword();

        /**
         * 获取注册的短信验证码
         * @return
         */
        public String getNoteCode();

        /**
         * 获取图形验证码
         * @return
         */
        public String getIdentifyingCode();

        /**
         * 展示账号输入错误时的提示
         * @param error
         */
        public void showAccError(String error);

        /**
         * 展示密码输入错误时的信息
         * @param error
         */
        public void showPasswordError(String error);

        /**
         * 展示短信验证码输入错误时的信息
         * @param error
         */
        public void showNoteCodeError(String error);

        /**
         * 展示图形验证码输入错误时的信息
         * @param error
         */
        public void showIdentityCodeError(String error);

        /**
         * 注册成功时
         */
        public void onRegistSuccess();
    }
    
    public interface RegistPresenter{

        /**
         * 开始账号密码注册
         */
        public void accRegist();

        /**
         * 获取短信验证码
         */
        public void getNoteCode();

        /**
         * 获取图形验证码
         */
        public void getIdentityCode();
    }

}
