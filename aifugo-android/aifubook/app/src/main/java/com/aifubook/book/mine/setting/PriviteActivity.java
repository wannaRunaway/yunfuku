package com.aifubook.book.mine.setting;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.product.ProductDetailsPresenter;
import com.aifubook.book.product.ProductDetailsView;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ListKer_Hlg on 2021/5/18 0:41
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
@Route(path = "/mine/setting/PriviteActivity")
public class PriviteActivity extends BaseActivity<ProductDetailsPresenter> implements ProductDetailsView {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_private;
    }

    // 获取隐私协议
    private void getConfigValue(String fig) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "" + fig);
        mPresenter.getConfigValue(map);
    }

    public void DialogShowDetails(String Message) {
        Log.e("SDAsdasdasdasda", "" + Message);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(false); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(Message);
    }


    @Override
    public void initPresenter() {
        mPresenter = new ProductDetailsPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("" + getIntent().getExtras().getString("title"));
        DialogShowDetails(getIntent().getExtras().getString("fig"));
    }

    @Override
    public void GetDataSuc(ProductDetailBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetShopSuc(ShopBean DataBean) {

    }

    @Override
    public void GetShopFail(String Message) {

    }

    @Override
    public void SendSuc(ProductListBean DataBean) {

    }

    @Override
    public void SendSucFail(String Message) {

    }

    @Override
    public void CardAddSuc(String DataBean) {

    }

    @Override
    public void StringRol(String DataBean) {
    }

    @Override
    public void CardAddFail(String Message) {

    }

    @Override
    public void getWeChatToken(String weChatToken) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
