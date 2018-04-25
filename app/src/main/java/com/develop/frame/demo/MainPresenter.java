package com.develop.frame.demo;

import android.widget.TextView;

import com.develop.frame.bases.BasePresenter;
import com.develop.frame.demo.deploy.TestDeploy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjh on 2018/4/24.
 */

public class MainPresenter extends BasePresenter<TestDeploy.TestViewS> implements TestDeploy.TestPresenter {

    public MainPresenter(TestDeploy.TestViewS view){
        super(view);
    }


    public void loadData(int page){
        List list = new ArrayList();
        for(int i = 0;i < 5;i++){
            TestModel model = new TestModel();
            model.id = "testmodel" + i;
            TestModel2 model2 = new TestModel2();
            model2.id = "testmodel2" + i;
            list.add(model);
            list.add(model2);
        }
        mView.showList(list);
    }


}
