package com.jiarui.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.jiarui.base.refresh.Pullable;
import com.jiarui.base.utils.DisplayUtil;

public class PullableWebView extends WebView implements Pullable {
    private Context mContext;

    public PullableWebView(Context context)
    {
        super(context);
        this.mContext = context;
    }

    public PullableWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
    }

    public PullableWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
    }


    @Override
    public boolean canPullDown()
    {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPullUp()
    {
        if (getScrollY() >= getContentHeight() * getScale() - getMeasuredHeight() - DisplayUtil.dip2px(mContext, 3))
            return true;
        else
            return false;
    }
}
