package com.aifubook.book.mine.address;

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

public class AddressModel implements BaseModel {

    /**
     * 获取用户所有收货地址
     */
    Subscription addressList(Map<String, String> requestData, RxSubscriber<List<AddressListBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .addressList(requestData)
                .compose(RxHelper.<List<AddressListBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取默认的收货地址
     */
    Subscription addressCurrent(Map<String, String> requestData, RxSubscriber<AddressListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .addressCurrent(requestData)
                .compose(RxHelper.<AddressListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取默认的收货地址
     */
    Subscription updateDefaultAddress(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateDefaultAddress(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 添加收货地址
     */
    Subscription addressAdd(Map<String, String> requestData, RxSubscriber<AddressListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .addressAdd(getRequestBody(requestData))
                .compose(RxHelper.<AddressListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 更新收货地址
     */
    Subscription addressUpdate(Map<String, String> requestData, RxSubscriber<AddressListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .addressUpdate(getRequestBody(requestData))
                .compose(RxHelper.<AddressListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 删除收货地址
     */
    Subscription addressDelete(Map<String, String> requestData, RxSubscriber<AddressListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .addressDelete(requestData)
                .compose(RxHelper.<AddressListBean>handleResult())
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
