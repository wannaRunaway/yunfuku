package com.jiarui.base.appcommon;

import android.content.Context;

/**
 * @author：admin on 2017/4/15 15:26.
 *
 */

public class GlobalAppComponent {

    public static boolean hasEnterLogin = false;//标记是否已进入登录页面
    public static boolean isAutoForwardLogin = true;//标记是否已进入登录页面


    private volatile static AppComponent mAppComponent;

    /**
     * 初始化全局AppComponent
     * @param context applicationContext
     */
    public static void init(Context context){
        if(mAppComponent == null){
            synchronized (GlobalAppComponent.class){
                if(mAppComponent == null){
                    mAppComponent = DaggerAppComponent.builder()
                            .applicationModule(new ApplicationModule(context.getApplicationContext()))
                            .build();
                }
            }
        }
    }

    public static AppComponent getAppComponent() {
        if(mAppComponent == null){
            throw (new NullPointerException("GlobalAppComponent必须在application中进行init初始化"));
        }
        return mAppComponent;
    }


}
