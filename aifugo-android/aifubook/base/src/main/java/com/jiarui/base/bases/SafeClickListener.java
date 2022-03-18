package com.jiarui.base.bases;

import java.util.Calendar;

import android.view.View;

/**
 * 防止快速点击
 */
public abstract class SafeClickListener implements ISafeClick {

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            safeClick(v);
        }
    }
}