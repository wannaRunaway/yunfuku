package com.aifubook.book.api;

import android.os.Environment;
import android.util.Log;

import com.aifubook.book.application.MyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiarui.base.AppConfig;
import com.jiarui.base.bases.BaseApplication;
import com.jiarui.base.gson.GsonConverterFactory;
import com.jiarui.base.rxjava.HttpLoggingInterceptor;
//import com.jiarui.base.rxjava.RxJavaCallAdapterFactory;
//import com.jiarui.base.rxjava.RxJavaCallAdapterFactory;
import com.jiarui.base.rxjava.RxJavaCallAdapterFactory;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.NetWorkUtils;
import com.jiarui.base.utils.PackageUtil;
import com.jiarui.base.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by ListKer-Hlg on 2018/5/9 0009.
 * 此类是作用于： Api的接口配置
 * 邮箱: 1425034784@qq.com
 */

public class Api {
    public Retrofit retrofit;
    public ApiService service;
    //    public final String CACHE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/Android/smart_canteen_sh/";
//    public final String HTTP_CACHE_PATH = CACHE_ROOT_PATH + "cache/";
    private static Api instance;

    public static Api getInstance() {
        if (instance == null) {
            synchronized (Api.class) {
                if (instance == null) {
                    instance = new Api();
                }
            }
        }
        return instance;
    }

    // 增加头部信息
    Interceptor headerInterceptor = chain -> {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("device", "android")
                .header("v", PackageUtil.getVersionName(MyApp.getInstance()))
//                .header("canteenId", ""+SharedPreferencesUtil.get(MyApp.getInstance(), "", ""))
                .header("token", "" + SharedPreferencesUtils.get(MyApp.getInstance(), "TOKEN", ""));
//                .header("token", ""+SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.LOGON_TOKEN, ""));
        requestBuilder.method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    };

    // 构造方法私有
    private Api() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        File cacheFile = new File(HTTP_CACHE_PATH);
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 100Mb
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.writeTimeout(20, TimeUnit.SECONDS);
//        builder.readTimeout(20, TimeUnit.SECONDS);
//        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(headerInterceptor);
        builder.addInterceptor(interceptor);//打印retrofit日志
        builder.addNetworkInterceptor(new HttpCacheInterceptor());
//        builder.cache(cache);
        OkHttpClient okHttpClient = builder.build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_HOST)
                //设置 gson 转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                //RxJava 适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).build();
        service = retrofit.create(ApiService.class);
    }

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                LogUtil.d("xuedi", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200").removeHeader("Pragma")
                        .build();
            }
        }
    }
}
