package com.jiarui.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.jiarui.base.appcommon.GlobalAppComponent;

public class ToastUtil {

    private static Toast toast = null;
    /**
     * 上一次时间
     */
    private static long lastTime = 0;
    /**
     * 当前时间
     */
    private static long curTime = 0;
    /**
     * 之前显示的内容
     */
    private static String oldMsg;

    public static void showToast(final String text, final boolean isLongTime) {
        final Context context = GlobalAppComponent.getAppComponent().getContext();
        if (isLongTime) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }



    /* */
    /**
     * 提示信息
     *//*
    public static void showToast(final String text, final boolean isLongTime) {
        final Context context = GlobalAppComponent.getAppComponent().getContext();
        final int y = ScreenUtil.getHeight() / 7;//ScreenUtil.getHeight(context)/8;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(context, text, isLongTime, y);
        } else {
            Looper.prepare();
            show(context, text, isLongTime, y);
            Looper.loop();
        }

    }*/

    /*private static void show(Context context, String text, final boolean isLongTime, final int y) {
        curTime = System.currentTimeMillis();
        int duration = isLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        JSONObject jsonObject = MessageUtil.getMessage();
        if (null != jsonObject) {
            text = jsonObject.optString(text, text);
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
            oldMsg = text;
        } else {
            if (text.equals(oldMsg)) {
                if (curTime - lastTime < duration) {
                    lastTime = System.currentTimeMillis();
                    return;
                }
            }

            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.setGravity(Gravity.BOTTOM, 0, y);
        lastTime = System.currentTimeMillis();
        toast.show();
    }*/

    /*
     * 自定义view的toast
     */
    /*public static void showTopToast(int resId, String content) {
        if (!StringUtil.checkStr(content))
            return;
        final Context context = GlobalAppComponent.getAppComponent().getContext();
        *//*int[] pos = {1, -1};
        v.getLocationOnScreen(pos);
        int x = pos[0];
        int y = pos[1];*//*
        View view;
            view = LayoutInflater.from(context).inflate(R.layout.toast_x_y, null);
        if (AppConfig.isWanse) {
            view = LayoutInflater.from(context).inflate(R.layout.toast_wanse, null);
        }

        int w = ScreenUtil.getWidth();
        int h = ScreenUtil.dip2px(context, 55.0f);

        RelativeLayout root_ll = view.findViewById(R.id.root_ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        root_ll.setLayoutParams(params);

        TextView text = view.findViewById(R.id.text);
      *//*  JSONObject jsonObject = MessageUtil.getMessageZh();
        if(null!=jsonObject){
            content = jsonObject.optString(content,content);
        }*//*
        text.setText(content + "");
        ImageView imageview = view.findViewById(R.id.imageview);
        if (resId > 0) {
            imageview.setBackgroundResource(resId);
        } else {
            imageview.setVisibility(View.GONE);
        }
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }*/

    /*
     * toast居中展示
     */
    private static Toast centerToast;

    public static void showCenterToast(String content) {
        if (!StringUtil.checkStr(content))
            return;
        if (null == centerToast) {
            final Context context = GlobalAppComponent.getAppComponent().getContext();
            TextView view = new TextView(context);
            view.setBackgroundColor(Color.parseColor("#000000"));
            view.setAlpha(0.75f);
            centerToast = new Toast(context);
            centerToast.setView(view);
        }
        centerToast.setGravity(Gravity.CENTER, 0, 0);
        centerToast.setDuration(Toast.LENGTH_LONG);
        centerToast.show();
    }
}
