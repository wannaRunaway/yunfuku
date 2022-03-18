package com.aifubook.book.utils;

import android.app.Application;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;


/*
 * alibaba arouter
 */
public class ArouterUtil {
    public static void init(Application application){
//        if (AppConfig.IS_DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
//        }

        ARouter.init(application); // As early as possible, it is recommended to initialize in the Application
    }

    /*
     * 拦截器模式路由
     */
    public static void navigation(String path, Bundle bundle){
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    /*
     * 无拦截器模式路由
     */
    public static void greenChannel(String path,Bundle bundle){
        ARouter.getInstance().build(path).with(bundle).greenChannel().navigation();
    }
}
