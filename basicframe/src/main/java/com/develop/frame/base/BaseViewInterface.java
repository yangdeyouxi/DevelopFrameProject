package com.develop.frame.base;

import android.os.Bundle;

/**
 * Created by zengh on 2018/2/1.
 */

public interface BaseViewInterface {

    int getLayoutId();

    void beforeInitView(Bundle savedInstanceState);

    void initView();

    void initData();

}
