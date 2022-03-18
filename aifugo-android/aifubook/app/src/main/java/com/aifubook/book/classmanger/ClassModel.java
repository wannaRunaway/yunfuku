package com.aifubook.book.classmanger;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.bean.getKidBean;
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

public class ClassModel implements BaseModel {


    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription findByCityId(Map<String, String> requestData, RxSubscriber<List<SchoolBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .findByCityId(requestData)
                .compose(RxHelper.<List<SchoolBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription childList(Map<String, String> requestData, RxSubscriber<List<ClassBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .childList(requestData)
                .compose(RxHelper.<List<ClassBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription findSchoolClasses(Map<String, Object> requestData, RxSubscriber<List<FindSchoolClassesBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .findSchoolClasses(requestData)
                .compose(RxHelper.<List<FindSchoolClassesBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription getChildById(Map<String, Object> requestData, RxSubscriber<getKidBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getChildById(requestData)
                .compose(RxHelper.<getKidBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription childDelete(Map<String, Object> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .childDelete(requestData)
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
    Subscription childUpdateChild(Map<String, Object> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .childUpdateChild(getRequestBody(requestData))
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
    Subscription childaddChild(Map<String, Object> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .childaddChild(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
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
