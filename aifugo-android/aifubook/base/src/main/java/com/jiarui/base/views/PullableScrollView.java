package com.jiarui.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.jiarui.base.refresh.Pullable;

public class PullableScrollView extends ScrollView implements Pullable {

    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*动态设置是否下拉刷新上拉加载*/
    private boolean canPullDown = true;// true:可以下拉;false:不可以下拉
    private boolean canPullUp = true;// true:可以上拉;false:不可以上拉

    public void setCanPullDown(boolean canPullDown) {
        this.canPullDown = canPullDown;
    }

    public void setCanPullUp(boolean canPullUp) {
        this.canPullUp = canPullUp;
    }

    @Override
    public boolean canPullDown() {

        if (isHorizontal)
            return false;

        if (!canPullDown)
            return false;

        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {

        if (isHorizontal)
            return false;

        if (!canPullUp)
            return false;

        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

    // 解决嵌套ViewPager的冲突
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    private boolean isHorizontal = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    isHorizontal = true;
                    return false;
                } else
                    isHorizontal = false;
        }
        return super.onInterceptTouchEvent(ev);
    }


    public interface ScrollViewChange {
        void onScrollViewChange(int l, int t, int oldl, int oldt);
    }

    public ScrollViewChange onScrollViewChange;

    public void setOnScrollViewChange(ScrollViewChange onScrollViewChange) {
        this.onScrollViewChange = onScrollViewChange;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollViewChange != null) {
            onScrollViewChange.onScrollViewChange(l, t, oldl, oldt);
        }
    }
}