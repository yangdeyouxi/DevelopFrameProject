package com.develop.frame.widget.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Zhang Qixiang on 2017/9/7.
 */

public class BaseLoadMoreRecyclerView extends RecyclerView {


    private Adapter mAdapter;
    private int mToLoadMoreLeftCount = 0;

    public BaseLoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public BaseLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(mLoadMoreListener);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
    }


    private LoadMoreListener mListener;

    public void setLoadMoreListener(LoadMoreListener listener) {
        mListener = listener;
    }

    private boolean hasLoadedAll = false;

    /**
     *设置true时后面再滑到底的时候不会再回调接口
     */
    public void hasLoadedAll(boolean hasLoadedAll) {
        this.hasLoadedAll = hasLoadedAll;
    }

    public interface LoadMoreListener {
        void doLoadMore();
    }

    protected boolean isInLoading = false;

    public void finishLoadMore() {
        isInLoading = false;
    }

    private OnScrollListener mLoadMoreListener = new OnScrollListener() {


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int pos ;

            LayoutManager lm = recyclerView.getLayoutManager();
            if (lm instanceof LinearLayoutManager) {
                pos = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
            } else if (lm instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) lm;
                int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                pos = getMax(lastPositions);
            } else {
                return;
            }


            if (dy > 0 && mListener != null && mAdapter != null && !isInLoading && !hasLoadedAll) {//dy<0表示向上滑
                int lastPos = mAdapter.getItemCount() - 1;//滑到最后加载
                if (pos >= (lastPos - mToLoadMoreLeftCount)) {
                    isInLoading = true;
                    mListener.doLoadMore();
                }
            }


        }
    };

    public static int getMax(int[] values) {
        int tmp = Integer.MIN_VALUE;

        if (null != values) {
            tmp = values[0];
            for (int value : values) {
                if (tmp < value) {
                    tmp = value;
                }
            }
        }
        return tmp;
    }

    /**
     * 设置还有多少项时加载下一页
     * @param count
     */
    public void setToloadMoreLeftCount(int count){
        mToLoadMoreLeftCount = count;
    }

}
