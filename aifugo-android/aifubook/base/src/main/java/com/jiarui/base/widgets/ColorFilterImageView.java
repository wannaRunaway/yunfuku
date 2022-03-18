package com.jiarui.base.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.jiarui.base.glide.GlideImageView;

import androidx.core.view.ViewCompat;

/**
 * @author WSB
 * @ClassName: ColorFilterImageView
 * @Description: 实现图像根据按下抬起动作变化颜色
 * @date 2017-06-19
 */
public class ColorFilterImageView extends GlideImageView {
    public ColorFilterImageView(Context context) {
        this(context, null, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            Drawable drawableUp = getDrawable();
            if (drawableUp != null) {
                drawableUp.clearColorFilter();
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

}
