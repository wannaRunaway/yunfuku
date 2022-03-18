package com.jiarui.base.rollviewpager.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.jiarui.base.R;
import com.jiarui.base.utils.DisplayUtil;


/**
 * Created by Mr.Jude on 2016/1/10.
 */
public class ColorPointHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;

    public ColorPointHintView(Context context, int focusColor, int normalColor) {
        super(context);
        this.focusColor = focusColor;
        this.normalColor = normalColor;
    }

    @Override
    public Drawable makeFocusDrawable() {
//        GradientDrawable dot_focus = new GradientDrawable();
//        dot_focus.setColor(focusColor);
//        dot_focus.setCornerRadius(DisplayUtil.dip2px(getContext(), 4));
//        dot_focus.setSize(DisplayUtil.dip2px(getContext(), 6), DisplayUtil.dip2px(getContext(), 6));
        return getResources().getDrawable(R.mipmap.xuanzhong);
    }

    @Override
    public Drawable makeNormalDrawable() {
//        GradientDrawable dot_normal = new GradientDrawable();
//        dot_normal.setColor(normalColor);
//        dot_normal.setCornerRadius(DisplayUtil.dip2px(getContext(), 4));
//        dot_normal.setSize(DisplayUtil.dip2px(getContext(), 6), DisplayUtil.dip2px(getContext(), 6));
        return getResources().getDrawable(R.mipmap.dian);
    }
}
