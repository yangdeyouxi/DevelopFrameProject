package com.develop.frame.demo;

import com.develop.frame.bases.IBasePresenter;
import com.develop.frame.demo.deploy.TestDeploy;
import com.develop.frame.fragment.BaseAdaptiveRecyclerViewFragment;

import java.util.List;

/**
 * Created by yangjh on 2018/4/24.
 */

public class MainFragment extends BaseAdaptiveRecyclerViewFragment<MainPresenter> implements TestDeploy.TestViewS {

    @Override
    public void initView() {

    }

    @Override
    public void initEventClick() {

    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void loadData(int page, int num, String lastId) {
        iBasePresenter.loadData(page);
    }

    @Override
    public void showList(List list) {
        onDataLoaded(list,getPage());
    }
}
