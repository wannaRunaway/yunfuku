package com.aifubook.book.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

import com.aifubook.book.R;
import androidx.annotation.ColorInt;

@SuppressLint("AppCompatCustomView")
public class PasswordUnderLineEditText extends EditText {

    public interface TextIndexChangeListener {
        void onTextIndexChange(CharSequence text, int index);
    }

    private TextIndexChangeListener mTextIndexChangeListener;

    @ColorInt
    private int lineColor;
    private int lineCount;
    private int lineMargin;
    private int lineHeight;
    private int linePaddingBottom;

    private String changedText;
    private int changedTextSize;
    private int changedTextColor;
    private int textLength;
    private boolean isHideLineOnChanged;

    private Paint mLinePaint;
    private TextPaint mChangedTextPaint;


    public PasswordUnderLineEditText(Context context) {
        this(context, null);
    }

    public PasswordUnderLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        lineColor = getResources().getColor(R.color.gray);
        changedTextColor = getResources().getColor(R.color.black);
        lineCount = 6;
        changedTextSize = DensityUtil.sp2px2(context, 20);
        lineMargin = DensityUtil.px2dp2(context, 12);
        linePaddingBottom = DensityUtil.px2dp2(context, 10);
        lineHeight = DensityUtil.px2dp2(context, 1);

        changedText = "*";
        isHideLineOnChanged = true;
        setGravity(Gravity.CENTER_VERTICAL);
        setTextColor(getResources().getColor(android.R.color.transparent));
        setBackgroundColor(Color.WHITE);
        setCursorVisible(false);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(lineCount)});

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(lineHeight);
        mChangedTextPaint = new TextPaint();
        mChangedTextPaint.setColor(changedTextColor);
        mChangedTextPaint.setTextSize(changedTextSize);
        mChangedTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        canvas.save();

        int singleLineWidth = width / lineCount;
        int startX = 0;
        int stopX = 0;
        int startY = height - linePaddingBottom;
        for (int i = 0; i < lineCount; i++) {
            if (textLength == 0) {
                startX = i * singleLineWidth + lineMargin;
                stopX = (i + 1) * singleLineWidth - lineMargin;
                canvas.drawLine(startX, startY, stopX, startY, mLinePaint);
            } else if (textLength > 0) {
                int changeIndex;
                if (isHideLineOnChanged) {
                    changeIndex = textLength;
                } else {
                    changeIndex = 0;
                }
                startX = (i + changeIndex) * singleLineWidth + lineMargin;
                stopX = (i + changeIndex + 1) * singleLineWidth - lineMargin;
                canvas.drawLine(startX, startY, stopX, startY, mLinePaint);
            }
        }
        for (int i = 0; i < textLength; i++) {
            startX = i * singleLineWidth + lineMargin;
            stopX = (i + 1) * singleLineWidth - lineMargin;
            canvas.drawText(changedText, (startX + stopX) / 2 - changedTextSize / 4, (height + changedTextSize) / 2, mChangedTextPaint);
        }

        canvas.restore();
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.textLength = text.length();
        invalidate();
        if (mTextIndexChangeListener != null) {
            mTextIndexChangeListener.onTextIndexChange(text, textLength);
        }
    }

}
