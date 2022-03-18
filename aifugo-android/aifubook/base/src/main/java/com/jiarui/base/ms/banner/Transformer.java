package com.jiarui.base.ms.banner;

import com.jiarui.base.ms.banner.transformer.AccordionTransformer;
import com.jiarui.base.ms.banner.transformer.BackgroundToForegroundTransformer;
import com.jiarui.base.ms.banner.transformer.CubeInTransformer;
import com.jiarui.base.ms.banner.transformer.CubeOutTransformer;
import com.jiarui.base.ms.banner.transformer.DefaultTransformer;
import com.jiarui.base.ms.banner.transformer.DepthPageTransformer;
import com.jiarui.base.ms.banner.transformer.FlipHorizontalTransformer;
import com.jiarui.base.ms.banner.transformer.FlipVerticalTransformer;
import com.jiarui.base.ms.banner.transformer.ForegroundToBackgroundTransformer;
import com.jiarui.base.ms.banner.transformer.RotateDownTransformer;
import com.jiarui.base.ms.banner.transformer.RotateUpTransformer;
import com.jiarui.base.ms.banner.transformer.ScaleInOutTransformer;
import com.jiarui.base.ms.banner.transformer.ScaleRightTransformer;
import com.jiarui.base.ms.banner.transformer.ScaleTransformer;
import com.jiarui.base.ms.banner.transformer.StackTransformer;
import com.jiarui.base.ms.banner.transformer.TabletTransformer;
import com.jiarui.base.ms.banner.transformer.ZoomInTransformer;
import com.jiarui.base.ms.banner.transformer.ZoomOutSlideTransformer;
import com.jiarui.base.ms.banner.transformer.ZoomOutTranformer;

import androidx.viewpager.widget.ViewPager;

public class Transformer {

    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Scale = ScaleTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleRight = ScaleRightTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;

}
