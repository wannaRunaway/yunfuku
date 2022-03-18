package com.jiarui.base.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.jiarui.base.ms.MyCsBannerViewPager;

/**
 * Toast统一管理类
 */
public class ToastUitl {

    private static Toast toast;

    private static Toast initToast(Context mContext, CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(mContext, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(Context mContext, CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(Context mContext, CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(Context mContext, CharSequence message, int duration) {
        initToast(mContext, message, duration).show();
    }
}
