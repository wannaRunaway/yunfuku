package com.jiarui.base.utils;

import android.graphics.drawable.Drawable;


import com.jiarui.base.appcommon.GlobalAppComponent;

import androidx.core.content.ContextCompat;

public class ContextUtil {

    public static String getString(int stringId){
        return GlobalAppComponent.getAppComponent().getContext().getString(stringId);
    }

    public static int getColor(int colorId){
        return ContextCompat.getColor(GlobalAppComponent.getAppComponent().getContext(),colorId);
    }

    public static Drawable getResource(int drawableId){
        return ContextCompat.getDrawable(GlobalAppComponent.getAppComponent().getContext(), drawableId);
    }
}
