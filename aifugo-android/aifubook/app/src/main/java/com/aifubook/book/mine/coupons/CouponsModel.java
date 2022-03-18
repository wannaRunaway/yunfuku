package com.aifubook.book.mine.coupons;

import android.util.Log;

import com.aifubook.book.api.Api;
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

public class CouponsModel implements BaseModel {

    /**
     * 获取所有店铺可用优惠券
     */
    Subscription shopCoupons(Map<String, String> requestData, RxSubscriber<List<MyCouponsBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .shopCoupons(requestData)
                .compose(RxHelper.<List<MyCouponsBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取所有店铺可用优惠券
     */
    Subscription fullSiteReduceCoupons(Map<String, String> requestData, RxSubscriber<List<MyCouponsBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .fullSiteReduceCoupons(requestData)
                .compose(RxHelper.<List<MyCouponsBean>>handleResult())
                .subscribe(mRxSubscriber);
    }



    /**
     * 用户领取优惠券
     */
    Subscription couponReceive(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .couponReceive(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 用户领取过的优惠券, 个人中心页面 优惠券
     */
    Subscription memberCoupons(Map<String, String> requestData, RxSubscriber<MemberCouponsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .memberCoupons(getRequestBody(requestData))
                .compose(RxHelper.<MemberCouponsBean>handleResult())
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
