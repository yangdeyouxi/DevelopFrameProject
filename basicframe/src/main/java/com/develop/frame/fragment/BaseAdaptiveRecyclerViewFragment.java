package com.develop.frame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.develop.frame.R;
import com.develop.frame.bases.BaseFragment;
import com.develop.frame.bases.IBasePresenter;
import com.develop.frame.widget.list.BaseRecyclerModel;
import com.develop.frame.widget.list.LoadMoreRecyclerView;
import com.develop.frame.widget.list.impl.LoadMoreRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


/**
 * Created by yangjiahuan on 2017/12/7.
 * 这个Fragment是基础的采用MVVM适配的Fragment,目前没有下啦刷新功能
 *
 */

public abstract class BaseAdaptiveRecyclerViewFragment<P extends IBasePresenter> extends BaseFragment<P> {

    public LoadMoreRecyclerView mRv;
    public SmartRefreshLayout swipeRefreshWidget;
    public LoadMoreRecyclerAdapter mBaseRecyclerAdapter;
    private TextView tv_neterror;
    private List<BaseRecyclerModel> mDatas = new ArrayList<BaseRecyclerModel>();

    int page = 1;
    int count = 20;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_loadmore_recyclerview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        mRv = mRootView.findViewById(R.id.rv);
        swipeRefreshWidget = mRootView.findViewById(R.id.swipe_refresh_widget);
        tv_neterror = mRootView.findViewById(R.id.tv_neterror);
        mRv.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void doLoadMore() {
                if(mDatas.size() <= 0){
                    loadData(page,count,null);
                }else{
                    String score = mDatas.get(mDatas.size()-1).getId();
                    loadData(page,count,score);
                }

            }
        });

        swipeRefreshWidget.setEnableLoadmore(false);
        swipeRefreshWidget.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setPage(1);
                loadData(getPage(),getCount(),null);
            }
        });
        tv_neterror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_neterror.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initData() {
        loadData(page,count,null);//初始化时加载第一页数据
    }


    public <T extends BaseRecyclerModel> void onDataLoaded(List<T> list,int page){
        if(page <= 1){
            onDataRefreshSuccess(list);
        }else{
            loadDataSuccess(list);
        }
    }


    private <T extends BaseRecyclerModel> void onDataRefreshSuccess(List<T> list){
        if(null ==mBaseRecyclerAdapter){
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter = new LoadMoreRecyclerAdapter(getActivity(),mDatas);
            mRv.setAdapter(mBaseRecyclerAdapter);
        }else{
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter.notifyDataSetChanged();
        }
        page = 2;//接下来是第二页
        mRv.hasLoadedAll(false);
        mRv.finishLoadMore();
        //当第一页的数据就不满数量时
        if(list.size() < count || list.size() <= 0){
            mRv.hasLoadedAll(true);
            mBaseRecyclerAdapter.setHasLoadAll(true);
        }
        finishRefresh();
    }

    private <T extends BaseRecyclerModel> void  loadDataSuccess(List<T> list){
        if(null ==mBaseRecyclerAdapter){
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter = new LoadMoreRecyclerAdapter(getActivity(),mDatas);
            mRv.setAdapter(mBaseRecyclerAdapter);
        }else{
            mDatas.addAll(list);
            mBaseRecyclerAdapter.notifyDataSetChanged();
        }
        page++;//页数增加
        mRv.finishLoadMore();
        if(list.size() < count || list.size() <= 0){
            mRv.hasLoadedAll(true);
            mBaseRecyclerAdapter.setHasLoadAll(true);
        }
    }

    /**
     * 自动刷新
     */
    public void autoRefresh(){
        if(null != swipeRefreshWidget){
            swipeRefreshWidget.autoRefresh();
        }
    }

    /**
     * 结束刷新
     */
    public void finishRefresh(){
        if(null != swipeRefreshWidget){
            swipeRefreshWidget.finishRefresh();
        }
    }

    public void showNetError(){
        tv_neterror.setVisibility(View.VISIBLE);
        tv_neterror.setText("net_error");
    }

    public void enableRefresh(boolean refresh){
        swipeRefreshWidget.setEnableRefresh(refresh);
    }

    public void enableLoanMore(boolean loadMore){
//        mBaseRecyclerAdapter.setHasLoadAll(loadMore);
        mRv.hasLoadedAll(loadMore);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public abstract void loadData(int page,int num,String lastId);


}
