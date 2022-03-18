package com.jiarui.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 实现单击返回 imageView.setOnPhotoTapListener(new OnPhotoTapListener() {
 *
 * @author Administrator
 * @Override public void onPhotoTap(View arg0, float arg1, float arg2) {
 * context.finish(); } });
 */
public class FixScaleBUGViewPager extends ViewPager {

    public FixScaleBUGViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public FixScaleBUGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            // uncomment if you really want to see these errors
            // e.printStackTrace();
            return false;
        }
    }

}
