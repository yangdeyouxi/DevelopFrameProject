package com.develop.frame.bases;

import java.util.List;

/**
 * Created by yangjiahuan on 2018/3/29.
 */

public interface IBaseListView extends IBaseView {

    /**
     * 数据加载完成时
     * @param list
     * @param page
     */
    public void onDataLoaded(List list, int page);
}
