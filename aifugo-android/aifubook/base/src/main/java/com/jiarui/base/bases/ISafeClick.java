package com.jiarui.base.bases;

import android.view.View;
import android.view.View.OnClickListener;

public interface ISafeClick extends OnClickListener {
    public int MIN_CLICK_DELAY_TIME = 800;

    public abstract void safeClick(View view);
}
