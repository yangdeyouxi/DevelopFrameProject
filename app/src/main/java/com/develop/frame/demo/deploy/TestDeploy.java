package com.develop.frame.demo.deploy;

import com.develop.frame.bases.IBaseView;

import java.util.List;

/**
 * Created by yangjh on 2018/4/24.
 */

public interface TestDeploy {

    public interface TestPresenter{

    }

    public interface TestViewS extends IBaseView{
        public void showList(List list);
    }

}
