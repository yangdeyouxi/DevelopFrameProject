package com.develop.frame.bases;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.frame.R;
import com.develop.frame.base.BaseLazyFragment;
import com.develop.frame.widget.list.BaseRecyclerModel;
import com.develop.frame.widget.list.LoadMoreRecyclerView;
import com.develop.frame.widget.list.impl.LoadMoreRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sam on 2018/3/27.
 */

public abstract class BaseRecyclerViewFragment<P extends IBasePresenter> extends BaseLazyFragment<P> implements IBaseListView {


//    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

//    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

//    @BindView(R.id.iv_load_error)
    ImageView mIvLoadError;

//    @BindView(R.id.ll_load_error)
    LinearLayout mLlLoadError;

//    @BindView(R.id.tv_message)
    TextView mTvMessage;


    private List<BaseRecyclerModel> mDatas = new ArrayList<BaseRecyclerModel>();

    public LoadMoreRecyclerAdapter mBaseRecyclerAdapter;
    int page = 1;
    int count = 20;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recyclerview;

    }
    public  void onDataLoaded(List list,int page){
        if(page <= 1){
            onDataRefreshSuccess(list);
        }else{
            loadDataSuccess(list);
        }
    }
    @Override
    public void initView() {

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mDatas.size()<=0){
                    loadData(page,count,null);
                }else{
                    String lastId=mDatas.get(mDatas.size()-1).getId();
                    loadData(page,count,lastId);
                }

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                loadData(page,count,null);
            }
        });
    }

    private void onDataRefreshSuccess(List list){
        if(null ==mBaseRecyclerAdapter){
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter = new LoadMoreRecyclerAdapter(getActivity(),mDatas);
            mRecyclerView.setAdapter(mBaseRecyclerAdapter);
        }else{
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter.notifyDataSetChanged();
        }
        page = 2;//接下来是第二页

        //当第一页的数据就不满数量时
        if(list.size() < count || list.size() <= 0){
            mRecyclerView.hasLoadedAll(true);
            mBaseRecyclerAdapter.setHasLoadAll(true);
        }
        finishRefresh();
    }
    private void  loadDataSuccess(List list){
        if(null ==mBaseRecyclerAdapter){
            mDatas.clear();
            mDatas.addAll(list);
            mBaseRecyclerAdapter = new LoadMoreRecyclerAdapter(getActivity(),mDatas);
            mRecyclerView.setAdapter(mBaseRecyclerAdapter);
        }else{
            mDatas.addAll(list);
            mBaseRecyclerAdapter.notifyDataSetChanged();
        }
        page++;//页数增加
        mRecyclerView.finishLoadMore();
        if(list.size() < count || list.size() <= 0){
            mRecyclerView.hasLoadedAll(true);
            mBaseRecyclerAdapter.setHasLoadAll(true);
        }
    }
    /**
     * 结束刷新
     */
    public void finishRefresh(){
        if(null != mRefreshLayout){
            mRefreshLayout.finishRefresh();
        }
    }

    public void showNetError(){
        mLlLoadError.setVisibility(View.VISIBLE);
        mTvMessage.setText("net_error");
    }


    public void enableRefresh(boolean refresh){
        mRefreshLayout.setEnableRefresh(refresh);
    }
    public void enableLoadMore(boolean loadmore){
        mRefreshLayout.setEnableLoadmore(loadmore);
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


    public void initData(){
        loadData(page,count,null);
    }


}
