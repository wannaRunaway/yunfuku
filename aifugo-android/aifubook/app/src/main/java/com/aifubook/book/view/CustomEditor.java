package com.aifubook.book.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomEditor extends View {
    private Paint linePaint = new Paint();
    private Paint pwPaint = new Paint();
    // 密码
    private StringBuffer password = new StringBuffer();
    private int mWidth = 0;
    private int mHeight = 0;
    private RectF oval = new RectF(); // 圆角对象
    // 密码圆点半径
    private int cornerRadius = 20;
    // 边框线宽
    private int mStrokeWidth = 2;

    public CustomEditor(Context context) {
        this(context, null);
    }

    public CustomEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pwPaint.setColor(Color.parseColor("#3e3e3e"));
        pwPaint.setAntiAlias(true);
        pwPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画最外层框
        drawBorder(canvas);
        // 画分割线
        drawCuttingLine(canvas);
        // 画密码
        drawPassWord(password, canvas);
    }

    private void drawPassWord(StringBuffer password, Canvas canvas) {
        for (int i = 0; i < password.length(); i++) {
            canvas.drawCircle((mWidth - mStrokeWidth) * (i * 2 + 1) / 12, (mHeight - mStrokeWidth) / 2, mHeight / 8,
                    pwPaint);
        }
    }

    private void drawBorder(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#acacac"));
        linePaint.setStrokeWidth(mStrokeWidth);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(cornerRadius, 0, mWidth - cornerRadius - mStrokeWidth, 0, linePaint);
        canvas.drawLine(0, cornerRadius, 0, mHeight - cornerRadius, linePaint);
        canvas.drawLine(mWidth - mStrokeWidth, cornerRadius, mWidth - mStrokeWidth, mHeight - cornerRadius, linePaint);
        canvas.drawLine(cornerRadius, mHeight - mStrokeWidth, mWidth - cornerRadius, mHeight - mStrokeWidth, linePaint);

        oval.left = 0;
        oval.top = 0;
        oval.right = cornerRadius * 2;
        oval.bottom = cornerRadius * 2;
        canvas.drawArc(oval, 180, 90, false, linePaint);

        oval.left = mWidth - cornerRadius * 2 - mStrokeWidth;
        oval.top = 0;
        oval.right = mWidth - mStrokeWidth;
        oval.bottom = cornerRadius * 2;
        canvas.drawArc(oval, 270, 90, false, linePaint);

        oval.left = 0;
        oval.top = mHeight - cornerRadius * 2 - mStrokeWidth;
        oval.right = cornerRadius * 2 + mStrokeWidth;
        oval.bottom = mHeight - mStrokeWidth;
        canvas.drawArc(oval, 90, 90, false, linePaint);

        oval.left = mWidth - cornerRadius * 2 - mStrokeWidth;
        oval.top = mHeight - cornerRadius * 2 - mStrokeWidth;
        oval.right = mWidth - mStrokeWidth;
        oval.bottom = mHeight - mStrokeWidth;
        canvas.drawArc(oval, 0, 90, false, linePaint);
    }

    private void drawCuttingLine(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#d9d9d9"));
        linePaint.setStrokeWidth(1);
        linePaint.setAntiAlias(true);
        for (int i = 1; i < 6; i++) {
            canvas.drawLine(mWidth * i / 6, 0, mWidth * i / 6, mHeight - 2, linePaint);
        }
    }

    public String getPassword() {
        return password.toString();
    }

    public void setPassword(int password) {
        // 如果为-1则删除
        if (password == -1) {
            if (this.password.length() != 0) {
                this.password.deleteCharAt(this.password.length() - 1);
            }
        } else if (password == -2) {
            if (this.password.length() != 0) {
                this.password.delete(0,this.password.length());
            }
        } else {
            if (this.password.length() < 6) {
                this.password.append(password);
            }
        }
        if (this.password.length() == 6) {
            onPasswordSixListener.inputOver(this.password.toString());
        }
        invalidate();
    }

    public OnPasswordInputOverListener onPasswordSixListener;

    public void setOnPasswordSixListener(OnPasswordInputOverListener onPasswordSixListener) {
        this.onPasswordSixListener = onPasswordSixListener;
    }

    // 监听是否已达到6位数
    public interface OnPasswordInputOverListener {
        void inputOver(String password);
    }

}
