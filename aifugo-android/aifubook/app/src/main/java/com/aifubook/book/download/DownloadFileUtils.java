package com.aifubook.book.download;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class DownloadFileUtils {

    private static final String TAG = DownloadFileUtils.class.getSimpleName();
    private static HashMap<Object, Call> mCallHashMap = new HashMap();
    private static DownloadFileUtils instance;
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());
    private AbsFileProgressCallback defaultFileProgressCallback = new AbsFileProgressCallback() {
        public void onSuccess(String result) {
        }

        public void onProgress(long bytesRead, long contentLength, boolean done) {
        }

        public void onFailed(String errorMsg) {
        }

        public void onStart() {
        }

        public void onCancle() {
        }
    };
    private DownloadModel downloadModel = new DownloadModel();

    private DownloadFileUtils() {
    }

    public static DownloadFileUtils with() {
        instance = new DownloadFileUtils();
        return instance;
    }

    public DownloadFileUtils url(String url) {
        this.downloadModel.setHttpUrl(url);
        return instance;
    }

    public DownloadFileUtils downloadPath(String filePath) {
        this.downloadModel.setDownloadPath(filePath);
        return instance;
    }

    public DownloadFileUtils tag(Object tag) {
        this.downloadModel.setTag(tag);
        return instance;
    }

    public DownloadFileUtils headers(Map<String, String> headersMap) {
        this.downloadModel.setHeadersMap(headersMap);
        return instance;
    }

    public DownloadFileUtils addHeader(String headerKey, String headerValue) {
        this.downloadModel.getHeadersMap().put(headerKey, headerValue);
        return instance;
    }

    public void execute(AbsFileProgressCallback fileProgressCallback) {
        if (fileProgressCallback == null) {
            fileProgressCallback = this.defaultFileProgressCallback;
        }

        this.downloadModel.setFileProgressCallback(fileProgressCallback);
        this.startDonwload();
    }

    private void startDonwload() {
        if (this.downloadModel == null) {
            throw new NullPointerException("OkhttpRequestModel初始化失败");
        } else {
            String httpUrl = this.downloadModel.getHttpUrl();
            Object tag = this.downloadModel.getTag();
            if (tag == null) {
                tag = httpUrl;
            }

            Map<String, String> headersMap = this.downloadModel.getHeadersMap();
            final String downloadPath = this.downloadModel.getDownloadPath();
            final AbsFileProgressCallback fileProgressCallback = this.downloadModel.getFileProgressCallback();
            OkHttpClient.Builder okhttpBuilder = getOkhttpDefaultBuilder();
            okhttp3.Request.Builder requestBuild = new okhttp3.Request.Builder();
            requestBuild.url(httpUrl);
            if (headersMap != null && headersMap.size() > 0) {
                Iterator var8 = headersMap.keySet().iterator();

                while(var8.hasNext()) {
                    String key = (String)var8.next();
                    requestBuild.addHeader(key, (String)headersMap.get(key));
                }
            }

            okhttpBuilder.addNetworkInterceptor(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), fileProgressCallback)).build();
                }
            });
            mUIHandler.post(new Runnable() {
                public void run() {
                    fileProgressCallback.onStart();
                }
            });
            Call call = okhttpBuilder.build().newCall(requestBuild.get().build());
            mCallHashMap.put(tag, call);
            call.enqueue(new Callback() {
                public void onFailure(final Call call, final IOException e) {
                    Log.e(DownloadFileUtils.TAG, "onFailure:" + e.toString());
                    DownloadFileUtils.mUIHandler.post(new Runnable() {
                        public void run() {
                            if (call.isCanceled()) {
                                fileProgressCallback.onCancle();
                            } else {
                                fileProgressCallback.onFailed(e.toString());
                            }

                        }
                    });
                }

                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
//                    int lenx = false;
                    FileOutputStream fos = null;
                    DownloadFileUtils.checkDownloadFilePath(downloadPath);

                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(downloadPath);
                        fos = new FileOutputStream(file);

                        int len;
                        for(long var10 = 0L; (len = is.read(buf)) != -1; var10 += (long)len) {
                            fos.write(buf, 0, len);
                        }

                        fos.flush();
                        DownloadFileUtils.mUIHandler.post(new Runnable() {
                            public void run() {
                                fileProgressCallback.onSuccess("");
                            }
                        });
                    } catch (final Exception var24) {
                        DownloadFileUtils.mUIHandler.post(new Runnable() {
                            public void run() {
                                Log.e(DownloadFileUtils.TAG, "onFailure:" + var24.getMessage());
                                if (var24.getMessage().equals("Socket closed")) {
                                    fileProgressCallback.onCancle();
                                } else {
                                    fileProgressCallback.onFailed(var24.toString());
                                }

                            }
                        });
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException var23) {
                        }

                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException var22) {
                        }

                    }

                }
            });
        }
    }

    private static void checkDownloadFilePath(String localFilePath) {
        File path = new File(localFilePath.substring(0, localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

    }

    @NonNull
    public static OkHttpClient.Builder getOkhttpDefaultBuilder() {
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[])null, new TrustManager[]{trustManager}, new SecureRandom());
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        } catch (KeyManagementException var6) {
            var6.printStackTrace();
        }

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30000L, TimeUnit.MILLISECONDS);
        builder.readTimeout(30000L, TimeUnit.MILLISECONDS);
        builder.writeTimeout(30000L, TimeUnit.MILLISECONDS);
        builder.sslSocketFactory(sslSocketFactory, trustManager);
        builder.hostnameVerifier(DO_NOT_VERIFY);
        return builder;
    }

    public static void cancle(Object tag) {
        try {
            if (mCallHashMap != null && mCallHashMap.size() > 0 && mCallHashMap.containsKey(tag)) {
                Call call = (Call)mCallHashMap.get(tag);
                if (call != null) {
                    if (!call.isCanceled()) {
                        call.cancel();
                    }

                    mCallHashMap.remove(tag);
                }
            }
        } catch (Exception var2) {
        }

    }

    public static void cancleAll() {
        try {
            if (mCallHashMap != null && mCallHashMap.size() > 0) {
                Set<Map.Entry<Object, Call>> keyEntries = mCallHashMap.entrySet();
                Iterator var1 = keyEntries.iterator();

                while(var1.hasNext()) {
                    Map.Entry<Object, Call> entry = (Map.Entry)var1.next();
                    Object key = entry.getKey();
                    Call call = (Call)entry.getValue();
                    if (call != null) {
                        if (!call.isCanceled()) {
                            call.cancel();
                        }

                        mCallHashMap.remove(key);
                    }
                }
            }
        } catch (Exception var5) {
        }

    }


}
