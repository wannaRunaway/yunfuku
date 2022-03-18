package com.jiarui.base.bases;


import android.view.View;
import android.widget.AdapterView;

public interface OnItemSafeClick extends AdapterView.OnItemClickListener {
    public int MIN_CLICK_DELAY_TIME = 800;

    public abstract void safeClick(AdapterView<?> parent, View view, int position, long id);
}
