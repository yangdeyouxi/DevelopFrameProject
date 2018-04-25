package com.develop.frame.widget.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yangjh on 2017/1/5.
 * 用于BaseRecyclerAdapter的适配的View
 */

public abstract class BaseRecyclerItemView<M extends BaseRecyclerModel> extends LinearLayout{

    //布局的View
    public View mLayout;

    public BaseRecyclerItemView(Context context) {
        super(context);
        initView(context);
    }

    public BaseRecyclerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseRecyclerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化View
     */
    private void initView(Context context){
        mLayout = LayoutInflater.from(context).inflate(getContentId(),this);
        //通过这种方式让自己的尺寸能够适应RecyclerView宽度
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        initViews(mLayout);
    }

    /**
     * 刷新显示，这里面获取对应显示的model
     */
    public void refreshShow(M model,int position){
        bindViewAndData(model,position);
    }

    public <T extends View> T findView(View view, int viewId){
        return (T)(view.findViewById(viewId));
    }

    /**
     * 初始化这个View的布局
     * @return
     */
    public abstract int getContentId ();

    /**
     * 初始化每个控件
     */
    public abstract void initViews(View layout);

    /**
     * 刷新视图的显示
     * @param model
     */
    public abstract void bindViewAndData(M model,int position);

}
