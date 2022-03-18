package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.base.CouponBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.productcar.cart.CartBean;
import com.aifubook.book.productcar.cart.CartFragmentModel;
import com.jiarui.base.baserx.RxSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ShopCarModel extends BaseModel {


    private OnCallBack callBack;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    private CartFragmentModel mModel;

    public ShopCarModel(CartFragmentModel model) {
        this.mModel = model;
    }

    public void getProductList(Map<String, Object> requestData, int type) {

        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>() {

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


    /**
     * 用户领取优惠券
     */
    public void couponReceive(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.couponReceive(requestData, new RxSubscriber<String>() {

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


    /**
     * 获取所有店铺可用优惠券
     */
    public void getShopReduceCoupons(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.getShopReduceCoupons(requestData, new RxSubscriber<List<CouponBean>>() {

                    @Override
                    protected void _onNext(List<CouponBean> DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    /**
     * 获取购物车信息
     */
    public void carGet(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.carGet(requestData, new RxSubscriber<CartBean>() {

                    @Override
                    protected void _onNext(CartBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }


    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void carAdd(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.carAdd(requestData, new RxSubscriber<String>() {

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

    /**
     * 重置购物车数量
     */
    public void carResetCount(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.carResetCount(getRequestBody(requestData), new RxSubscriber<>() {

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


    /**
     * 删除购物车商品 直接删除或者单个商品减到0
     */
    public void carRemove(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.carRemove(requestData, new RxSubscriber<String>() {


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

    public RequestBody getRequestBody(Map<String, String> hashMap) {
        StringBuffer data = new StringBuffer();
        JSONObject jsonObjects = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
//                data.append(key).append("=").append(val).append("&");
                try {
                    jsonObject.put(key + "", val);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            jsonObjects.put("param", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String jso = data.substring(0, data.length() - 1);
//        Log.e("httphttphttp", "http" + jsonObjects);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return requestBody;
    }
}
