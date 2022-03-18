package com.jiarui.base.utils;

import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间工具包
 * Created by Administrator on 2018/3/6.
 */

public class TimeUtil {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatMsecConvertTime(long time) {
        return formatMsecConvertTime("yyyy-MM-dd HH:mm:ss", time);
    }

    public static String formatMsecConvertTime2(long time) {
        return formatMsecConvertTime("yyyy-MM-dd", time);
    }

    //毫秒转变时间
    public static String formatMsecConvertTime(String pattern, long time) {
        format.applyPattern(pattern);
        return format.format(new Date(time));
    }

    /**
     * 在按钮上启动一个定时器
     *
     * @param tvVerifyCode  验证码控件
     * @param defaultString 按钮上默认的字符串
     * @param max           失效时间（单位：s）
     * @param interval      更新间隔（单位：s）
     */
    public static void startTimer(final WeakReference<TextView> tvVerifyCode, final String defaultString, int max, int interval) {
        tvVerifyCode.get().setEnabled(false);
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        new CountDownTimer(max * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                if (null == tvVerifyCode.get())
                    this.cancel();
                else
                    tvVerifyCode.get().setText("已发送(" + Html.fromHtml("<font color='#FF8645'>" + ((time + 15) / 1000) + "</font>") + "s" + ")");
//                    tvVerifyCode.get().setText("" + Html.fromHtml("<font color='#FF8645'>" + ((time + 15) / 1000) + "</font>") + " 秒 后重新发送");
            }

            @Override
            public void onFinish() {
                if (null == tvVerifyCode.get()) {
                    this.cancel();
                    return;
                }
                tvVerifyCode.get().setEnabled(true);
                tvVerifyCode.get().setText(defaultString);

            }
        }.start();
    }
}
