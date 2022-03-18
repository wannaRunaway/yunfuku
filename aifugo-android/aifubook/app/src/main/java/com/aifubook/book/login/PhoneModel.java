package com.aifubook.book.login;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.bean.SchoolBean;
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

public class PhoneModel implements BaseModel {


    /**
     * 注册成为团长
     * @param requestData
     * @param mRxSubscriber
     * @return
     */
    Subscription registerApply(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .apply(getRequestBody(requestData))
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
    Subscription sendSmsCode(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
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
    Subscription loginHttp(Map<String, String> requestData, RxSubscriber<LoginHomePageBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .UserLogin(requestData)
                .compose(RxHelper.<LoginHomePageBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 手机号注册
     */
    Subscription userRegister(Map<String, String> requestData, RxSubscriber<LoginHomePageBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .userRegister(getRequestBody(requestData))
                .compose(RxHelper.<LoginHomePageBean>handleResult())
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
    public Subscription weChatLoginForApp(Map<String, Object> requestData, RxSubscriber<LoginHomePageBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .weChatLoginForApp(requestData)
                .compose(RxHelper.<LoginHomePageBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    public Subscription loginNew(Map<String, String> requestData, RxSubscriber<LoginHomePageBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .loginNew(getRequestBody(requestData))
                .compose(RxHelper.<LoginHomePageBean>handleResult())
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
    Subscription childaddChild(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .childaddChild(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }
}
