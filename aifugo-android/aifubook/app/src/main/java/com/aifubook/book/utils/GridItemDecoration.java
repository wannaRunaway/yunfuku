package com.aifubook.book.utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;

import com.aifubook.book.view.GridDecoration;

import androidx.annotation.ColorInt;

public class GridItemDecoration extends GridDecoration {

    public GridItemDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
        super(context, lineWidthDp, colorRGB);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        boolean[] booleans = {false, false, false, false};

        switch (itemPosition % 2) {
            case 0:
                //每一行第二个只显示左边距和下边距
                booleans[0] = true;
                booleans[3] = true;
                break;
            case 1:
                //每一行第一个显示右边距和下边距
                booleans[2] = true;
                booleans[3] = true;
                break;
        }
        return booleans;
    }
}
