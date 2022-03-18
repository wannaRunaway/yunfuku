package com.aifubook.book.model;

import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseModel;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.utils.OkHttpUtils;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ProductDetailsModel extends BaseModel {

    private static final String TAG = "ProductDetailsModel";
    private OnCallBack callBack;
    private com.aifubook.book.product.ProductDetailsModel model;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public ProductDetailsModel(com.aifubook.book.product.ProductDetailsModel model) {
        this.model = model;
    }

    public void getWechatAccessToken(int type) {
        LogUtil.e(TAG, "getWechatAccessToken");

        String url = ApiService.BASE_HOST + "config/wechatAccessToken";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String access_token = obj.optString("result");
                    callBack.onNext(access_token, type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                callBack.onError(e.getMessage(), type);
            }
        };

        OkHttpUtils.get(url, resultCallback);
    }

    public void productDetail(Map<String, String> requestData, int type) {
        mRxManage.add(
                model.productDetail(requestData, new RxSubscriber<ProductDetailBean>() {

                    @Override
                    protected void _onNext(ProductDetailBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void shopDetail(Map<String, String> requestData, int type) {
        mRxManage.add(
                model.shopDetail(requestData, new RxSubscriber<ShopBean>() {

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void productList(Map<String, Object> requestData, int type) {
        mRxManage.add(
                model.productList(requestData, new RxSubscriber<ProductListBean>() {

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void carAdd(Map<String, String> requestData,int type) {
        mRxManage.add(
                model.carAdd(requestData, new RxSubscriber<String>() {


                    @Override
                    protected void _onNext(String DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void getConfigValue(Map<String, String> requestData,int type) {
        mRxManage.add(
                model.getConfigValue(requestData, new RxSubscriber<String>() {

                    @Override
                    protected void _onNext(String DataBean) {
                      callBack.onNext(DataBean,type);
                    }

                    @Override
                    protected void _onError(String message) {
                       callBack.onError(message,type);
                    }
                }));
    }


}
