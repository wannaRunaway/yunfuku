package com.jiarui.base.bases;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Fresco.initialize(this);
    }

    public static Context getAppContext() {
        return baseApplication;
    }

}