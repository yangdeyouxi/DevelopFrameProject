package com.develop.frame.demo;

import com.develop.frame.widget.list.BaseRecyclerModel;

/**
 * Created by yangjh on 2018/4/24.
 */

public class TestModel2 extends BaseRecyclerModel<TestView2> {

    public String id;

    @Override
    public String getId() {
        return id;
    }
}
