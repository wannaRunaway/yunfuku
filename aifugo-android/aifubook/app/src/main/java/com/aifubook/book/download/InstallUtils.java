package com.aifubook.book.download;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class InstallUtils {

    private static final String TAG = InstallUtils.class.getSimpleName();
    private static InstallUtils mInstance;
    private static Context mContext;
    private String httpUrl;
    private String filePath;
    private static DownloadCallBack mDownloadCallBack;
    private static boolean isDownloading = false;

    private InstallUtils() {
    }

    public static boolean isDownloading() {
        return isDownloading;
    }

    public static void setDownloadCallBack(DownloadCallBack downloadCallBack) {
        if (isDownloading) {
            mDownloadCallBack = downloadCallBack;
        }

    }

    public static InstallUtils with(Context context) {
        mContext = context.getApplicationContext();
        if (mInstance == null) {
            mInstance = new InstallUtils();
        }

        return mInstance;
    }

    public InstallUtils setApkUrl(String apkUrl) {
        this.httpUrl = apkUrl;
        return mInstance;
    }

    public InstallUtils setApkPath(String apkPath) {
        this.filePath = apkPath;
        return mInstance;
    }

    public InstallUtils setCallBack(DownloadCallBack downloadCallBack) {
        mDownloadCallBack = downloadCallBack;
        return mInstance;
    }

    public void startDownload() {
        if (isDownloading) {
            cancleDownload();
        }

        if (TextUtils.isEmpty(this.filePath)) {
//            this.filePath = MNUtils.getCachePath(mContext) + "/update.apk";
        }

        MNUtils.changeApkFileMode(new File(this.filePath));
        DownloadFileUtils.with().downloadPath(this.filePath).url(this.httpUrl).tag(InstallUtils.class).execute(new AbsFileProgressCallback() {
            int currentProgress = 0;

            public void onSuccess(String result) {
                InstallUtils.isDownloading = false;
                if (InstallUtils.mDownloadCallBack != null) {
                    InstallUtils.mDownloadCallBack.onComplete(InstallUtils.this.filePath);
                }

            }

            public void onProgress(long bytesRead, long contentLength, boolean done) {
                InstallUtils.isDownloading = true;
                if (InstallUtils.mDownloadCallBack != null) {
                    int progress = (int) (bytesRead * 100L / contentLength);
                    if (progress - this.currentProgress >= 1) {
                        InstallUtils.mDownloadCallBack.onLoading(contentLength, bytesRead);
                    }

                    this.currentProgress = progress;
                }

            }

            public void onFailed(String errorMsg) {
                InstallUtils.isDownloading = false;
                if (InstallUtils.mDownloadCallBack != null) {
                    InstallUtils.mDownloadCallBack.onFail(new Exception(errorMsg));
                }

            }

            public void onStart() {
                InstallUtils.isDownloading = true;
                if (InstallUtils.mDownloadCallBack != null) {
                    InstallUtils.mDownloadCallBack.onStart();
                }

            }

            public void onCancle() {
                InstallUtils.isDownloading = false;
                if (InstallUtils.mDownloadCallBack != null) {
                    InstallUtils.mDownloadCallBack.cancle();
                }

            }
        });
    }

    public static void cancleDownload() {
        DownloadFileUtils.cancle(InstallUtils.class);
    }

    public static void openFile(Activity context, String filePath, final InstallCallBack callBack) {
        try {

            File apkFile = new File(filePath);
            Uri apkUri;

        } catch (Exception var7) {
            if (callBack != null) {
                callBack.onFail(var7);
            }
        }

    }


    public interface InstallPermissionCallBack {
        void onGranted();

        void onDenied();
    }

    public interface InstallCallBack {
        void onSuccess();

        void onFail(Exception var1);
    }

    public interface DownloadCallBack {
        void onStart();

        void onComplete(String var1);

        void onLoading(long var1, long var3);

        void onFail(Exception var1);

        void cancle();
    }
}
