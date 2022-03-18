package com.aifubook.book.activity.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.utils.StatusBarCompat;
import com.aifubook.book.utils.StatusBarUtil;
import com.jiarui.base.bases.BasePresenter;

public class TeacherCheckActivity extends AppCompatActivity {
    private WebView webView;
//    private String url_test = "https://chargingapp.klenergy.com.cn/share/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_check);
        StatusBarCompat.translucentStatusBar(this);
        StatusBarUtil.setStatusTextColor(false, this);
        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.loadUrl(ApiService.URL_CHECK);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}