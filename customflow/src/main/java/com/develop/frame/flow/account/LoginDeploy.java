package com.develop.frame.flow.account;

import com.develop.frame.flow.base.BaseView;

/**
 * Created by yangjh on 2018/5/1.
 * 通用MVP登陆模块deploy
 */
public interface LoginDeploy {

    public interface LoginView extends BaseView {

        /**
         * 获取登陆的账号
         * @return
         */
        public String getLoginAcc();

        /**
         * 获取登陆的密码
         * @return
         */
        public String getLoginPassword();

        /**
         * 获取登陆的短信验证码
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
         * 登陆成功
         */
        public void onLoginSuccess();

    }

    public interface LoginPresenter{

        /**
         * 开始账号密码登陆
         */
        public void accLogin();

        /**
         * 获取短信验证码
         */
        public void getNoteCode();

        /**
         * 获取图形验证码
         */
        public void getIdentityCode();

        /**
         * 开始第三方登陆：QQ、微博、微信
         */
        public void thirdLogin();
    }

}
