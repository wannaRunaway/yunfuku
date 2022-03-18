package com.jiarui.base.glide.progress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.jiarui.base.glide.GlideCatchConfig;

import java.io.InputStream;


@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    /**
     * 禁用清单解析
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        // 让你的APP比默认多20%的缓存
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2)
                .build();// 内存大小的计算器
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();// 获得内存缓存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();// 获取Bitmap池大小
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        // 设置解码格式
//        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setImageDecoderEnabledForBitmaps(true);

        // 自定义磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, GlideCatchConfig.IMAGE_CATCH_DIR, GlideCatchConfig.GLIDE_CATCH_SIZE));


    }
}