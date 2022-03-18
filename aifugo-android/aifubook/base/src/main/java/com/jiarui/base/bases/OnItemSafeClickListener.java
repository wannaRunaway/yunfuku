package com.jiarui.base.bases;


import android.view.View;
import android.widget.AdapterView;

import java.util.Calendar;

/**
 * listView与GrideView防止快速点击
 */
public abstract class OnItemSafeClickListener implements OnItemSafeClick {

    private long lastClickTime = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            safeClick(parent, view, position, id);
        }
    }
}
