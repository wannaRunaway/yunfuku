package com.aifubook.book.productcar.trueorder;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
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

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class PayOrderModel implements BaseModel {

    /**
     * 获取用户基本信息
     */
    Subscription memberInfo(Map<String, String> requestData, RxSubscriber<MemberInfoBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .memberInfo(requestData)
                .compose(RxHelper.<MemberInfoBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 修改用户信息
     */
    Subscription updateMemberInfo(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateMemberInfo(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取可用余额
     */
    Subscription getCanUsedBalance(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getCanUsedBalance(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 拿到createOrder 返回的支付订单支付
     */
    Subscription orderToPay(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderToPayObject(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 拿到createOrder 返回的支付订单支付
     */
    Subscription orderToPayWeChat(Map<String, String> requestData, RxSubscriber<SendRechargeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderToPayWeChat(getRequestBody(requestData))
                .compose(RxHelper.<SendRechargeBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 获取用户账户信息
     */
    Subscription getAccountInfo(Map<String, String> requestData, RxSubscriber<GetAccountInfoBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getAccountInfo(requestData)
                .compose(RxHelper.<GetAccountInfoBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 判断用户当前有没有设置支付密码
     */
    Subscription hasPayPassword(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .hasPayPassword(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 设置支付密码
     */
    Subscription setPayPassword(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .setPayPassword(requestData)
                .compose(RxHelper.<String>handleResult())
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
