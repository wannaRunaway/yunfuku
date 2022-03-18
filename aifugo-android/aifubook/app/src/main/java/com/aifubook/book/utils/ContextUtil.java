package com.aifubook.book.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jiarui.base.appcommon.GlobalAppComponent;

import androidx.core.content.ContextCompat;


public class ContextUtil {


    public static String getString(Context context, int stringId){
        return context.getResources().getString(stringId);
    }

    public static int getColor(Context context, int colorId){
        return context.getResources().getColor(colorId);
    }

    public static Drawable getResource(Context context, int drawableId){
        return context.getResources().getDrawable(drawableId);
    }

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
