package com.jiarui.base.rxjava;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jiarui.base.bases.BaseApplication;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.SharedPreferencesUtils;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * An OkHttp interceptor which logs request and response information. Can be
 * applied as an {@linkplain OkHttpClient#interceptors() application
 * interceptor} or as a {@linkplain OkHttpClient#networkInterceptors() network
 * interceptor}.
 * <p>
 * The format of the logs created by this class should not be considered stable
 * and may change slightly between releases. If you need a stable logging
 * format, use your own interceptor.
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }

        if (response.header("token") != null) {
            Log.e("WhatMessage",""+response.header("token"));
            SharedPreferencesUtils.put(BaseApplication.getAppContext(),"TOKEN",response.header("token"));
        }
        ResponseBody responseBody = response.body();
        String rBody;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);
        //request
        RequestBody requestBody = chain.request().body();
        String bodyrequest = null;
        if (requestBody != null) {
            Buffer bufferrequest = new Buffer();
            requestBody.writeTo(bufferrequest);
            Charset charsetrequest = UTF8;
            MediaType contentTyperequest = requestBody.contentType();
            if (contentTyperequest != null) {
                charsetrequest = contentTyperequest.charset(UTF8);
            }
            bodyrequest = bufferrequest.readString(charsetrequest);
        }
        LogUtil.dall("收到: method：" + request.method()+
                        "\n响应request:" + bodyrequest+
                "\n响应header：" + response.header("ACCESS_TOKEN")
                + "\n响应url:" + response.request().url()
                + "\n响应body: " + rBody);
        return response;
    }
}