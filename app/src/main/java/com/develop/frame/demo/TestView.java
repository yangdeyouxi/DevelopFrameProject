package com.develop.frame.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.develop.frame.R;
import com.develop.frame.widget.list.BaseRecyclerItemView;

/**
 * Created by yangjh on 2018/4/24.
 */

public class TestView extends BaseRecyclerItemView<TestModel> {

    TextView text;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getContentId() {
        return R.layout.testitem;
    }

    @Override
    public void initViews(View layout) {
        text = layout.findViewById(R.id.myId);
    }

    @Override
    public void bindViewAndData(TestModel model, int position) {
        text.setText("ssss" + model.id);
    }
}
