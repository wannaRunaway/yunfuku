package com.jiarui.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class PackageUtil {


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 获取该程序的安装包路径
     */
    public static String getPackagePath(Context context) {
        // PackageManager pm = context.getPackageManager();
        return context.getPackageResourcePath();
    }

    /*
     * 获取当前程序路径
     */
    public static String getCurApplicationPath(Context context) {
        // PackageManager pm = context.getPackageManager();
        return context.getFilesDir().getAbsolutePath();
    }

    /*
     * 得到应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            if (null != manager) {
                PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                        0);
                if (null != info) {
                    return info.versionName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 得到应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * 得到应用的渠道名称
     */
    public static String getChannelName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (null != packageManager) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        return applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回false 表示没有找到匹配这个url的apk
     * @param intent
     * @param context
     * @return
     */
    public static boolean checkUrlScheme(Intent intent, Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }


}
