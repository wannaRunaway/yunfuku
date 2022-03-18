package com.aifubook.book.product;

import android.graphics.BitmapFactory;

import com.aifubook.book.Constants;
import com.aifubook.book.api.Api;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.WeChatToken;
import com.aifubook.book.utils.OkHttpUtils;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class ProductDetailsPresenter extends BasePresenter<ProductDetailsView, ProductDetailsModel> {

    private static final String TAG = "ProductDetailsPresenter";

    public ProductDetailsPresenter(ProductDetailsView view) {
        setVM(view, new ProductDetailsModel());
    }


    public void getWechatAccessToken() {
        LogUtil.e(TAG, "getWechatAccessToken");
        /*mRxManage.add(mModel.getWechatAccessToken(new RxSubscriber<WeChatToken>(mContext) {
            @Override
            protected void _onNext(WeChatToken weChatToken) {
                LogUtil.e(TAG, "token==" + weChatToken.toString());
                mView.getWeChatToken(weChatToken);
            }

            @Override
            protected void _onError(String message) {
                LogUtil.e(TAG, "error=" + message);

            }
        }));*/

        String url = ApiService.BASE_HOST+"config/wechatAccessToken";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String access_token = obj.optString("result");
                    if(mView!=null) {
                        mView.getWeChatToken(access_token);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OkHttpUtils.get(url, resultCallback);
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.productDetail(requestData, new RxSubscriber<ProductDetailBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ProductDetailBean DataBean) {
                        mView.stopLoading();
                        mView.GetDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void shopDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.shopDetail(requestData, new RxSubscriber<ShopBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        mView.stopLoading();
                        mView.GetShopSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetShopFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productList(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        mView.stopLoading();
                        mView.SendSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.SendSucFail(message);
                    }
                }));
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void carAdd(Map<String, String> requestData) {
        mRxManage.add(
                mModel.carAdd(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CardAddSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void getConfigValue(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getConfigValue(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.StringRol(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }


}
