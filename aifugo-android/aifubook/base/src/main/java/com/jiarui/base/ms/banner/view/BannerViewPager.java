package com.jiarui.base.ms.banner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.jiarui.base.R;

import androidx.viewpager.widget.ViewPager;

//https://github.com/siralam/LoopingViewPager/blob/master/loopingviewpager/src/main/java/com/asksira/loopingviewpager/LoopingViewPager.java
public class BannerViewPager extends ViewPager {

    private boolean scrollable = true;
    protected boolean wrapContent = true;
    private float aspectRatio;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Banner, 0, 0);
        try {

            aspectRatio = a.getFloat(R.styleable.Banner_viewpagerAspectRatio, 0f);
            wrapContent = a.getBoolean(R.styleable.Banner_wrap_content, true);

        }finally {
            a.recycle();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return this.scrollable && super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return this.scrollable && super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setAspectRatio(float aspectRatio){
        this.aspectRatio=aspectRatio;
        invalidate();
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (aspectRatio > 0) {
            int height = Math.round((float) MeasureSpec.getSize(widthMeasureSpec) / aspectRatio);
            int finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(finalWidthMeasureSpec, finalHeightMeasureSpec);
        } else {
            //https://stackoverflow.com/a/24666987/7870874
            if (wrapContent) {
                int mode = MeasureSpec.getMode(heightMeasureSpec);
                // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
                // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
                if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
                    // super has to be called in the beginning so the child views can be initialized.
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    int height = 0;
                    // Remove padding from width
                    int childWidthSize = width - getPaddingLeft() - getPaddingRight();
                    // Make child width MeasureSpec
                    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
                    for (int i = 0; i < getChildCount(); i++) {
                        View child = getChildAt(i);
                        child.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                        int h = child.getMeasuredHeight();
                        if (h > height)  {
                            height = h;
                        }
                    }
                    // Add padding back to child height
                    height += getPaddingTop() + getPaddingBottom();
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                }
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
