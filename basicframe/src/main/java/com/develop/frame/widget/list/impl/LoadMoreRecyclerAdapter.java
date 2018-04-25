package com.develop.frame.widget.list.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.develop.frame.R;
import com.develop.frame.widget.list.BaseRecyclerAdapter;
import com.develop.frame.widget.list.BaseRecyclerModel;

import java.util.List;

/**
 * Created by yangjiahuan on 2018/1/12.
 */

public class LoadMoreRecyclerAdapter extends BaseRecyclerAdapter {

    //构造函数
    public LoadMoreRecyclerAdapter(Context context, List<BaseRecyclerModel> list){
        super(context,list);
        getFooterView();
    }


    @Override
    public View getFooterView() {
        initView();
        return mFooterView;
    }

    private ProgressBar mPbLoading;
    private TextView mTvLoadMore;
    private boolean hasLoadAll = false;

    private void initView(){
        if(null == mFooterView){
            mFooterView = LayoutInflater.from(mContext).inflate(R.layout.recylerview_footer, null);
            mPbLoading = mFooterView.findViewById(R.id.pb_loading);
            mTvLoadMore = mFooterView.findViewById(R.id.tv_load_more);
        }
    }

    private void onBind(Boolean loadedAll) {
        int pbVisible = !loadedAll ? View.VISIBLE : View.INVISIBLE;
        int textId = !loadedAll ? R.string.loading_more : R.string.loading_no_more;
        if(null != mPbLoading) {
            mPbLoading.setVisibility(pbVisible);
        }
        if(null != mTvLoadMore) {
            mTvLoadMore.setText(textId);
            mTvLoadMore.setVisibility(pbVisible);
        }
    }

    public void setHasLoadAll(boolean hasLoadAll) {
        this.hasLoadAll = hasLoadAll;
        onBind(hasLoadAll);
    }
}
