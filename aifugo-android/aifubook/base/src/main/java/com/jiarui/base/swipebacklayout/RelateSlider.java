package com.jiarui.base.swipebacklayout;

import android.os.Build;

/**
 */
public class RelateSlider implements SwipeListener {
    public SwipeBackPage curPage;
    private int offset = 500;

    public RelateSlider(SwipeBackPage curActivity) {
        this.curPage = curActivity;
        //curPage.addListener(this);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setEnable(boolean enable){
        if (enable)curPage.addListener(this);
        else curPage.removeListener(this);
    }

    @Override
    public void onScroll(float percent, int px) {
        if (Build.VERSION.SDK_INT>11){
            SwipeBackPage page = SwipeBackHelper.getPrePage(curPage);
            if (page!=null){
                page.getSwipeBackLayout().setX(-offset * Math.max(1 - percent,0));
                if (percent == 0){
                    page.getSwipeBackLayout().setX(0);
                }
            }
        }
    }

    @Override
    public void onEdgeTouch() {

    }

    @Override
    public void onScrollToClose() {
        SwipeBackPage page = SwipeBackHelper.getPrePage(curPage);
        if (Build.VERSION.SDK_INT>11) {
            if (page != null) page.getSwipeBackLayout().setX(0);
        }
    }
}
