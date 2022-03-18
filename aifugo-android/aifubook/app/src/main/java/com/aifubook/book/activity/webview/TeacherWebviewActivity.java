package com.aifubook.book.activity.webview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.shopdetail.IosDialog;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.fragment.groupheader.GroupHeaderActivity;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.mine.order.GlideEngine;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.StatusBarCompat;
import com.aifubook.book.utils.StatusBarUtil;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ToastUitl;

import java.util.ArrayList;

@SuppressLint("SetJavaScriptEnabled")
public class TeacherWebviewActivity extends AppCompatActivity {
    private String message;
    private String code;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ImageView imageview_left;
    private TextView header_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_webview);
        imageview_left = findViewById(R.id.imageview_left);
        header_textview = findViewById(R.id.header_textview);
        imageview_left.setOnClickListener(v -> {
            finish();
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);//与父容器的上侧对齐
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        WebView webView = new WebView(this);
        webView.setLayoutParams(lp);
        RelativeLayout content = findViewById(R.id.content);
        content.addView(webView);
//        webView = findViewById(R.id.webView);
        code = getIntent().getStringExtra(IntentKey.Companion.getINVITECODE());
        String token = SharedPreferencesUtil.get(this, KeyCom.LOGON_TOKEN, "");
        if (code == null || code.equals("")) {
            message = "token=" + token;
        } else {
            message = "token=" + token + "&" + code;
        }
        StatusBarCompat.translucentStatusBar(this);
        StatusBarUtil.setStatusTextColor(false, this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
//        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient() {
            // 本人以为5.0以前的大可不必处理，现在新建的android项目minSDK都建议22（也就是android5.0）起步了，再说了目前的环境先5.0以前的系统还真不好找，除了老年机
            // For Android 5.0+
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                new IosDialog(TeacherWebviewActivity.this).builder().setGone()
                        .setTitle("提示").setMsg("请选择相册或者相机")
                        .setNegativeButton("相机", v -> {
                            EasyPhotos.createCamera(TeacherWebviewActivity.this, false)
                                    .setFileProviderAuthority("com.aifubook.book.cameraprovider")//参数说明：见下方`FileProvider的配置`
                                    .start(new SelectCallback() {
                                        @Override
                                        public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                            Uri[] results = new Uri[]{photos.get(0).uri};
                                            mUploadCallbackAboveL.onReceiveValue(results);
                                            mUploadCallbackAboveL = null;
                                        }

                                        @Override
                                        public void onCancel() {
                                            mUploadCallbackAboveL.onReceiveValue(null);
                                            mUploadCallbackAboveL = null;
                                            ToastUitl.showShort(MyApp.getInstance(), "您没有选择图片");
                                        }
                                    });
                        }).setPositiveButton("相册", v -> {
                            EasyPhotos.createAlbum(TeacherWebviewActivity.this, false, false, GlideEngine.getInstance())
//                                    .setFileProviderAuthority("com.aifubook.book.fileprovider")//参数说明：见下方`FileProvider的配置`
                                    .setCount(1)//参数说明：最大可选数，默认1
                                    .start(new SelectCallback() {
                                        @Override
                                        public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                            Uri[] results = new Uri[]{photos.get(0).uri};
                                            mUploadCallbackAboveL.onReceiveValue(results);
                                            mUploadCallbackAboveL = null;
                                        }

                                        @Override
                                        public void onCancel() {
                                            mUploadCallbackAboveL.onReceiveValue(null);
                                            mUploadCallbackAboveL = null;
                                            ToastUitl.showShort(MyApp.getInstance(), "您没有选择图片");
                                        }
                                    });
                        })
                        .setCancelable(true).show();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                header_textview.setText(title);
            }
        });
        String host = "";
        if (ApiService.BASE_HOST.equals("https://api.aifubook.com/")) {
            host = ApiService.HOSTBASE;
        } else {
            host = ApiService.HOST90;
        }
        //通过interface和h5通信
        HybridAPI hybridAPI = new HybridAPI(this);
        webView.addJavascriptInterface(hybridAPI, "HybridAPI");
        String url = host + ApiService.weburl + message;
        webView.loadUrl(url);
        Log.d("xuedi", url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
    }
}