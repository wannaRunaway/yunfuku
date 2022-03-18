package com.aifubook.book.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.jiarui.base.utils.LogUtil;

import java.util.List;

public class PackageUtil {

    private static final String TAG = "PackageUtil";

    public static String getAppPackageName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        LogUtil.e(TAG, "当前应用:" + componentInfo.getPackageName());
        return componentInfo.getPackageName();
    }

}
