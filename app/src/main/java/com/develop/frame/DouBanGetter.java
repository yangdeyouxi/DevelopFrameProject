package com.develop.frame;

import android.util.Log;

import com.develop.frame.demo.TestDouBanService;
import com.develop.frame.network.RxHttpUtils;
import com.develop.frame.network.bean.ErrorBean;
import com.develop.frame.network.observer.SingleObserver;
import com.develop.frame.network.transformer.Transformer;


/**
 * Created by yangjh on 2018/4/26.
 */

public class DouBanGetter {


    private TestDouBanService testDouBanService;

    public DouBanGetter() {
        testDouBanService = RxHttpUtils.getInstance().createApi(TestDouBanService.class);
    }

    public void gett() {
        testDouBanService
                .getFollowingArticles()
                .compose(Transformer.switchSchedulers())
                .subscribe(new SingleObserver<String>() {

                    @Override
                    protected void onFail(ErrorBean errorBean) {
                        Log.i("testTag", "onFail: " + errorBean.getMessage());
                    }

                    @Override
                    protected void onSuccess(String data) {
                        Log.i("testTag", "onSuccess: " + data);
                    }
                });
    }

}
