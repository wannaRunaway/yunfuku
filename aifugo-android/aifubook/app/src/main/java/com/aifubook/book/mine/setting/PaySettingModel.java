package com.aifubook.book.mine.setting;

import android.util.Log;

import com.aifubook.book.api.Api;
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

public class PaySettingModel implements BaseModel {

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription sendSmsCode(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
        return Api.getInstance()
                .service
                .sendSmsCode(requestData)
                .compose(RxHelper.<Integer>handleResult())
                .subscribe(mRxSubscriber);
    }

    public Subscription sendMobileCode(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
        return Api.getInstance()
                .service
                .sendSmsCode(requestData)
                .compose(RxHelper.<Integer>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription updateMemberEmail(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateMemberEmail(requestData)
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
    public Subscription checkCode(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .checkCode(requestData)
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
    Subscription appBindWeChat(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .appBindWeChat(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 手机号注册
     */
    Subscription setPayPassword(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .setPayPassword(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 发送注册验证码
     */
    Subscription sendRegCode(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
        return Api.getInstance()
                .service
                .sendRegCode(requestData)
                .compose(RxHelper.<Integer>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 发送注册验证码
     */
    Subscription updateMobile(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateMobile(requestData)
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
    Subscription sendSmsCodeTrue(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
        return Api.getInstance()
                .service
                .sendSmsCode(requestData)
                .compose(RxHelper.<Integer>handleResult())
                .subscribe(mRxSubscriber);
    }


//    /**
//     * 获得评测列表
//     *
//     * @param requestData   报文
//     * @param mRxSubscriber 订阅
//     * @return 获得到订阅者
//     */
//    Subscription updateLoginPassword(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
//        return Api.getInstance()
//                .service
//                .updateLoginPassword(requestData)
//                .compose(RxHelper.<String>handleResult())
//                .subscribe(mRxSubscriber);
//    }

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
