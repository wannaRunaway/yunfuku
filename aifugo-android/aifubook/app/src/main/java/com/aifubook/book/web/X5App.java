package com.aifubook.book.web;

import android.content.Context;

import com.jiarui.base.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

public class X5App {

    public void initX5app(Context context) {
        //设置非wifi条件下允许下载X5内核
        QbSdk.setDownloadWithoutWifi(true);

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtil.e("QbSdk", "onDownloadFinish -->下载X5内核完成：" + i);
                if(i!=100){
                    TbsDownloader.startDownload(context);
                }

            }

            @Override
            public void onInstallFinish(int i) {
                LogUtil.e("QbSdk", "onInstallFinish -->安装X5内核进度：" + i);

            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtil.e("QbSdk", "onDownloadProgress -->下载X5内核进度：" + i);

            }
        });

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean load) {
                //x5內核初始化完成的回调，true表x5内核加载成功，否则表加载失败，会自动切换到系统内核。
                LogUtil.e("app", " 内核加载 " + load);


            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        //x5内核初始化接口
        QbSdk.initX5Environment(context, cb);
    }

}
