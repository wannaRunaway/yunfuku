package com.aifubook.book.activity.main.money;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;

import com.aifubook.book.R;

public class MySeekbar extends AppCompatSeekBar {
    private TextPaint paint;
    private String text = "请按住滑块，拖动到最右边";
    public MySeekbar(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new TextPaint();
    }

    public MySeekbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySeekbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawText(text,0,0,paint);
    }
}
