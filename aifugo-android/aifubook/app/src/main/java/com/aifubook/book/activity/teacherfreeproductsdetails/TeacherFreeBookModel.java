package com.aifubook.book.activity.teacherfreeproductsdetails;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.WeChatToken;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;

public class TeacherFreeBookModel implements BaseModel {


    /**
     * 获取微信token
     * @param mRxSubscriber
     * @return
     */
    public Subscription getWechatAccessToken(RxSubscriber<WeChatToken> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getWechatAccessToken()
                .compose(RxHelper.<WeChatToken>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription productDetail(Map<String, String> requestData, RxSubscriber<ProductDetailBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .productDetail(requestData)
                .compose(RxHelper.<ProductDetailBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription shopDetail(Map<String, String> requestData, RxSubscriber<ShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .shopDetail(requestData)
                .compose(RxHelper.<ShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 创建订单
     */
    public Subscription createOrder(RequestBody requestData, RxSubscriber<CreateOrderBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .createOrder(requestData)
                .compose(RxHelper.<CreateOrderBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    //获取地址
    /**
     * 获取用户默认收货地址
     */
    public Subscription getDefaultAddress(RxSubscriber<AddressListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getDefaultAddress()
                .compose(RxHelper.<AddressListBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public Subscription getConfigValue(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getConfigValue(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public Subscription carAdd(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .carAdd(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription productList(Map<String, Object> requestData, RxSubscriber<ProductListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .productList(getRequestBody(requestData))
                .compose(RxHelper.<ProductListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    public RequestBody getRequestBody(Map<String, Object> hashMap) {
        StringBuffer data = new StringBuffer();
        JSONObject jsonObjects = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
                try {
                    jsonObject.put(key + "", val + "");
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
        String jso = data.substring(0, data.length() - 1);
//        Log.e("httphttphttp", "http" + jsonObjects);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return requestBody;
    }

}
