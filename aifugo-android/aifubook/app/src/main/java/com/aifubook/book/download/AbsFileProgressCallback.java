package com.aifubook.book.download;

public abstract class AbsFileProgressCallback {

    public AbsFileProgressCallback() {
    }

    public abstract void onSuccess(String var1);

    public abstract void onProgress(long var1, long var3, boolean var5);

    public abstract void onFailed(String var1);

    public abstract void onStart();

    public abstract void onCancle();

}
