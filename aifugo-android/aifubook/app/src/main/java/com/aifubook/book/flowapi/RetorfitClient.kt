package com.aifubook.book.flowapi

import android.os.Environment
import com.aifubook.book.api.ApiService
import com.aifubook.book.application.MyApp
import com.jiarui.base.AppConfig
import com.jiarui.base.bases.BaseApplication
import com.jiarui.base.gson.GsonConverterFactory
import com.jiarui.base.rxjava.HttpLoggingInterceptor
import com.jiarui.base.utils.LogUtil
import com.jiarui.base.utils.NetWorkUtils
import com.jiarui.base.utils.PackageUtil
import com.jiarui.base.utils.SharedPreferencesUtils
import okhttp3.*


import retrofit2.Retrofit
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

val CACHE_ROOT_PATH = Environment.getExternalStorageDirectory().path + "/Android/smart_canteen_sh/"
val HTTP_CACHE_PATH = CACHE_ROOT_PATH + "cache/"
val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(ApiService.BASE_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build();
}

private fun getOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    val cacheFile: File = File(HTTP_CACHE_PATH)
    val cache = Cache(cacheFile, 1024 * 1024 * 100) // 100Mb
    val builder = OkHttpClient.Builder()
    builder.writeTimeout(20, TimeUnit.SECONDS)
    builder.readTimeout(20, TimeUnit.SECONDS)
    builder.connectTimeout(15, TimeUnit.SECONDS)
    builder.addInterceptor(headerInterceptor)
    if (AppConfig.IS_DEBUG) {
        builder.addInterceptor(interceptor) //打印retrofit日志
    }
    builder.addNetworkInterceptor(HttpCacheInterceptor())
    builder.cache(cache)
//    val okHttpClient = builder.build()
//    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()
    return builder.build()
}

// 增加头部信息
var headerInterceptor = Interceptor { chain: Interceptor.Chain ->
    val originalRequest = chain.request()
    val requestBuilder = originalRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("device", "android")
        .header(
            "v",
            PackageUtil.getVersionName(MyApp.getInstance().applicationContext)
        ) //                .header("canteenId", ""+SharedPreferencesUtil.get(MyApp.getInstance(), "", ""))
        .header(
            "token",
            "" + SharedPreferencesUtils.get(
                MyApp.getInstance().applicationContext,
                "TOKEN",
                ""
            )
        )
//                .header("token", ""+SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.LOGON_TOKEN, ""));
    requestBuilder.method(originalRequest.method(), originalRequest.body())
    val request = requestBuilder.build()
    chain.proceed(request)
}

internal class HttpCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetWorkUtils.isNetConnected(MyApp.getInstance().applicationContext)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            LogUtil.d("Okhttp", "no network")
        }
        val originalResponse = chain.proceed(request)
        return if (NetWorkUtils.isNetConnected(MyApp.getInstance().applicationContext)) {
            // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder().header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build()
        } else {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                .removeHeader("Pragma")
                .build()
        }
    }
}

