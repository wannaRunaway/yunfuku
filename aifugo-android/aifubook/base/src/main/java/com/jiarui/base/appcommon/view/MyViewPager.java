package com.jiarui.base.appcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/***/

public class MyViewPager extends ViewPager {

    private int autoScrollDuration = 4000;

    public void setAutoScrollDuration(int autoScrollDuration) {
        this.autoScrollDuration = autoScrollDuration;
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isAutoScroll;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoScroll) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    removeCallbacks(mRunnable);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    startScroll();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }



    public void onPause(){
        isAutoScroll = false;
        removeCallbacks(mRunnable);
    }

    public void onResume(){
        isAutoScroll = true;
        startScroll();
    }

    private void startScroll(){
        if(getAdapter() != null && getAdapter().getCount() > 1 && isAutoScroll){
            removeCallbacks(mRunnable);
            postDelayed(mRunnable,autoScrollDuration);
        }
    }

    /**
     * 供外部调用
     */
    public void startAutoScroll(){
        isAutoScroll = true;
        startScroll();
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            if(adapter != null && adapter.getCount() > currentItem){
                setCurrentItem(currentItem + 1,true);
                startScroll();
            }
        }
    };

}
