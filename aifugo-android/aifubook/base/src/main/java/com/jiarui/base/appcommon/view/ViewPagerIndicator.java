package com.jiarui.base.appcommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.jiarui.base.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 */

public class ViewPagerIndicator extends LinearLayout implements BasePagerAdapter.AdapterNotifyListener, ViewPager.OnPageChangeListener {

    //普通状态id
    private int mNormalDrawableId;
    //选中状态id
    private int mSelectedDrawableId;

    private float margin ;
    private BasePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int realSize;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicators, 0, 0);
        if(a != null){
            mNormalDrawableId = a.getResourceId(R.styleable.ViewPagerIndicators_normalDrawable,R.drawable.view_pager_indicator_normal);
            mSelectedDrawableId = a.getResourceId(R.styleable.ViewPagerIndicators_selectedDrawable,R.drawable.view_pager_indicator_selected);
            margin = a.getDimension(R.styleable.ViewPagerIndicators_layout_itemMargin,dpToPx(5));
            a.recycle();
        }
    }

    /**
     * 设置指示器两种状态drawableId
     * @param normalDrawableId 常规
     * @param selectedDrawableId 选中
     */
    public void setStateDrawableId(@DrawableRes int normalDrawableId, @DrawableRes int selectedDrawableId){
        this.mNormalDrawableId = normalDrawableId;
        this.mSelectedDrawableId = selectedDrawableId;
    }

    public void setAdapterAndViewPager(BasePagerAdapter pagerAdapter, ViewPager viewPager){
        mPagerAdapter = pagerAdapter;
        mViewPager = viewPager;
        if(mPagerAdapter != null){
            mPagerAdapter.setListener(this);
            initView();
        }

        if(viewPager != null){
            viewPager.addOnPageChangeListener(this);
        }
    }

    private void initView(){
        if(mPagerAdapter != null && mViewPager !=null && mPagerAdapter.getRealSize() > 1){
            this.removeAllViews();
            realSize = mPagerAdapter.getRealSize();
            for (int i = 0; i < realSize; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setBackground(getStateListDrawable());
                if(i == 0){
                    imageView.setSelected(true);
                }else{
                    LayoutParams params = new LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins((int) margin,0,0,0);
                    imageView.setLayoutParams(params);
                }
                addView(imageView);
            }
        }
    }

    private StateListDrawable getStateListDrawable(){
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_selected},getContext().getResources().getDrawable(mSelectedDrawableId));
        sd.addState(new int[]{},getContext().getResources().getDrawable(mNormalDrawableId));
        return sd;
    }


    @Override
    public void notifyChange() {
        initView();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if(view != null){
                view.setSelected(i == position % realSize);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int dpToPx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp +0.5f);
    }
}
