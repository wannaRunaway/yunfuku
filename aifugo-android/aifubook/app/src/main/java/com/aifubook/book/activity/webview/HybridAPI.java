package com.aifubook.book.activity.webview;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.aifubook.book.fragment.groupheader.GroupHeaderActivity;
import com.jiarui.base.utils.LogUtil;
import com.youth.banner.util.LogUtils;

public class HybridAPI {
    private TeacherWebviewActivity activity;
    public HybridAPI(TeacherWebviewActivity activity){
        this.activity = activity;
    }
    //string
    @JavascriptInterface
    public void sendToNative(final String message) {
        switch (message){
            case "去申请":
                activity.finish();
                activity.startActivity(new Intent(activity, GroupHeaderActivity.class));
                break;
            case "跳过":
                activity.finish();
                break;
        }
        LogUtil.d("get data from js------------>" + message);
    }
}
