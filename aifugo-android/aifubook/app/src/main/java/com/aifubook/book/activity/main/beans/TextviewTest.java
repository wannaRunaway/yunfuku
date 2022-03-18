package com.aifubook.book.activity.main.beans;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.aifubook.book.R;
import com.example.constraintcorner.ConstraintArcShape;

public class TextviewTest extends androidx.appcompat.widget.AppCompatTextView {

    private Paint paint;
//    private int color;
    private boolean isshow;
    public TextviewTest(Context context) {
        super(context);
        init(context, null);
    }

    public TextviewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextviewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.underlineTextView);
        int color = 0;
        if (typedArray !=null){
            color = typedArray.getColor(R.styleable.underlineTextView_undercover, ContextCompat.getColor(context,R.color.black));
            isshow = typedArray.getBoolean(R.styleable.underlineTextView_show, false);
        }
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected()) {
//            canvas.drawLine(10, getWidth() - 10, getHeight(), getHeight() + 10, paint);
            canvas.drawLine(10, 50000, 0, 10000, paint);
        }
    }
}
