package com.develop.frame.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by czk on 2017/1/5.
 */

public class BaseRecyclerAdapter<T extends BaseRecyclerModel> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<T> mDatas;
    public View mHeaderView;
    public View mFooterView;
    private boolean hideFootView = false;

    protected Context mContext;
    private OnItemClickListener mOnItemClickListener;

    //构造函数
    public BaseRecyclerAdapter(Context context, List<T> list){
        this.mDatas = list;
        this.mContext = context;
    }

    public void refreshData(List<T> list){
        mDatas = list;
//        mDatas.clear();
//        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            Log.i("tagg","getItemViewType:" + mDatas.get(position).getViewType());
            return mDatas.get(position).getViewType();
        }
        //因为用了下拉刷新的GroupView，所以这里暂时不需要
        if (position == 0 && null != mHeaderView){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1 && null != mFooterView && !hideFootView){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return mDatas.get(position).getViewType();
//        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public BaseRecyclerAdapter.BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseHolder(mHeaderView);
        }
        if(getFooterView() != null && viewType == TYPE_FOOTER){
            return new BaseHolder(mFooterView);
        }
        View layout = BaseRecyclerPresenter.getInstance().createView(mContext,viewType);
        return new BaseHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseHolder holder, int position) {
        Log.i("tagg","getItemViewType:" + getItemViewType(position));
        if(getItemViewType(position) == TYPE_FOOTER){
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            if(holder instanceof BaseRecyclerAdapter.BaseHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                ((BaseHolder)holder).tv.setText(mDatas.get(position-1));
                //总觉得这里会有BUG
                holder.showView.refreshShow(mDatas.get(position),position);
                initClick(holder.showView,mDatas.get(position));
                return;
            }

        }
    }

    private void initClick(View view,final Object obj){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mOnItemClickListener){
                    mOnItemClickListener.onClick(obj);
                }
            }
        });
    }

    //在这里面加载ListView中的每个item的布局
    public class BaseHolder extends RecyclerView.ViewHolder{

        BaseRecyclerItemView showView;

        public BaseHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            showView = (BaseRecyclerItemView)itemView;
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mDatas.size() == 0){
            return 0;
        }
        if(mHeaderView == null && mFooterView == null){
            return mDatas.size();
        }else if(mHeaderView == null && mFooterView != null && hideFootView){
            return mDatas.size();
        }else if(mHeaderView == null && mFooterView != null && !hideFootView){
            return mDatas.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return mDatas.size() + 1;
        }else {
            return mDatas.size() + 2;
        }
    }

    /**
     * 隐藏加载更多
     */
    public void hideFootView(){
        if(null != mFooterView){
//            mFooterView.setVisibility(View.GONE);
            hideFootView = true;
            notifyDataSetChanged();
        }
    }

    /**
     * 隐藏加载更多
     */
    public void showFootView(){
        if(null != mFooterView){
//            mFooterView.setVisibility(View.VISIBLE);
            hideFootView = false;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener<T>{
        public void onClick(T item);
    }

}
