package com.jiarui.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class ScrollExpandListView extends ExpandableListView {

    public ScrollExpandListView(Context context)
    {
        super(context);
    }

    public ScrollExpandListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ScrollExpandListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

}
