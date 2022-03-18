package com.aifubook.book.productcar;


import com.aifubook.book.bean.SendRechargeBean;
import com.alibaba.fastjson.JSONArray;
import com.aifubook.book.api.Api;
import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.bean.MyChileBean;
import com.aifubook.book.bean.SameBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class TrueOrderModel implements BaseModel {


    public Subscription orderToPayWeChat(Map<String, String> requestData, RxSubscriber<SendRechargeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderToPayWeChat(getRequestBody(requestData))
                .compose(RxHelper.<SendRechargeBean>handleResult())
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

    /**
     * 同城配送 获取配送费
     */
    public Subscription getSameCityFeeForApp(Map<String, String> requestData, RxSubscriber<SameBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getSameCityFeeForApp(getRequestBody(requestData))
                .compose(RxHelper.<SameBean>handleResult())
                .subscribe(mRxSubscriber);
    }

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
     * 获取用户所有收货地址
     */
    public Subscription memberUnUsedCoupons(Map<String, String> requestData, RxSubscriber<List<CouponBeans>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .memberUnUsedCoupons(requestData)
                .compose(RxHelper.<List<CouponBeans>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取用户所有收货地址
     */
    public Subscription myChief(Map<String, String> requestData, RxSubscriber<MyChileBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .myChief(requestData)
                .compose(RxHelper.<MyChileBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 团长详情信息
     */
    public Subscription chiefDetail(Map<String, String> requestData, RxSubscriber<ChiefDetailsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .chiefDetail(requestData)
                .compose(RxHelper.<ChiefDetailsBean>handleResult())
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
