package com.aifubook.book.mine;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ServiceBean;
//import com.jiarui.base.smartrefres.SmartRefreshLayout;
//import com.jiarui.base.smartrefres.api.RefreshLayout;
//import com.jiarui.base.smartrefres.listener.OnRefreshLoadmoreListener;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ListKer_Hlg on 2021/5/13 1:45
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ServiceActivity extends BaseActivity<ServicePresenter> implements ServiceView, OnRefreshLoadMoreListener {

    @BindView(R.id.refreshLayout)   // 刷新控件
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<ServiceBean.list> beanBaseRecyclerAdapter;
    List<ServiceBean.list> productListBeans;

    private int pageNum = 1;
    private int pageSize = 10;

    private boolean isRefresh = true;//是否刷新
    private boolean isLoad = false;//是否加载

    @Override
    public int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    public void initPresenter() {
mPresenter = new ServicePresenter(this);
    }

    @Override
    public void initView() {
        setTitle("系统公告");
        productListBeans = new ArrayList<>();
        initRighRecycler();
        productList();
    }

    // 发送验证码接口
    private void  productList(){
        Map<String, String> map = new HashMap<>();
        map.put("offset",""+0);
        map.put("pageSize",""+200);
        map.put("status",""+1);
        mPresenter.noticeList(map);
    }

    private void initRighRecycler() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ServiceBean.list>(this, productListBeans, R.layout.activity_service_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ServiceBean.list item, int position, boolean isScrolling) {
                holder.setText(R.id.Title,item.getTitle());
                WebView webView = holder.getView(R.id.webView);
                DialogShowDetails(item.getContent(),webView);

                holder.setText(R.id.Time, TimeUtil.formatMsecConvertTime2(item.getCreateTime()));
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(beanBaseRecyclerAdapter);
    }

    public void DialogShowDetails(String Message,WebView webView) {
        Log.e("SDAsdasdasdasda",""+Message);
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

        String detailHtml = StringUtils.isNull(Message);
//图片宽度改为100%  高度为自适应
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        webView.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }


    @Override
    public void GetDataSuc(ProductDetailBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetShopSuc(ServiceBean DataBean) {
        productListBeans.clear();
        productListBeans.addAll(DataBean.getList());
        beanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetShopFail(String Message) {
        ToastUitl.showShort(this,Message+"!");
    }

    @Override
    public void GetListDataSuc(ProductListBean DataBean) {

    }

    @Override
    public void GetListDataFail(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }
}
