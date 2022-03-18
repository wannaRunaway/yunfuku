package com.aifubook.book.regimental.mvp;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.ChiefMembersBean;
import com.aifubook.book.regimental.ChiefMyChiefBean;
import com.aifubook.book.regimental.ChiefOrderByCodeBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.regimental.RechargeBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

public class HeadApplyModel implements BaseModel {

    /**
     * 团长申请 手机号必填A
     */
    Subscription chiefApply(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .apply(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 上传图片
     */
    Subscription uploadImage(Map<String, String> requestData, List<File> filelist, RxSubscriber<String> mRxSubscriber) {
        RequestBody requestBody;
        LinkedHashMap<String, RequestBody> maps = new LinkedHashMap<>();
        for (int i = 0; i < filelist.size(); i++) {
            Log.e("JPEGJPEGJPEG", "" + filelist.get(i));
            // 获取图片类型
            int index = filelist.get(i).getName().lastIndexOf(".");
            String fileType = filelist.get(i).getName().substring(index + 1);
            if (fileType.equals("JPEG")) {
                fileType = "png";
            } else if (fileType.equals("jpg")) {
                fileType = "jpg";
            } else {
                fileType = "mp4";
            }
            if (fileType.equals("mp4")) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), filelist.get(i));
            } else {
                requestBody = RequestBody.create(MediaType.parse("image/" + fileType), filelist.get(i));
            }
            maps.put("image" + "\";filename=\"" + filelist.get(i).getName(), requestBody);
        }
        return Api.getInstance()
                .service
                .uploadImageSingle(requestData, maps)
                .compose(RxHelper.<String>handleResult())
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
     * 团长介绍的用户的分页查询
     */
    Subscription chiefMembers(Map<String, String> requestData, RxSubscriber<ChiefMembersBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .chiefMembers(getRequestBody(requestData))
                .compose(RxHelper.<ChiefMembersBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取用户的团长
     */
    Subscription chiefMyChief(Map<String, String> requestData, RxSubscriber<ChiefMyChiefBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .chiefMyChief(requestData)
                .compose(RxHelper.<ChiefMyChiefBean>handleResult())
                .subscribe(mRxSubscriber);
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
    Subscription sendSmsCode(Map<String, String> requestData, RxSubscriber<Integer> mRxSubscriber) {
        return Api.getInstance()
                .service
                .sendSmsCode(requestData)
                .compose(RxHelper.<Integer>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 团长根据核销码查询订单详情
     *
     */
    Subscription getChiefOrderByCode(Map<String, String> requestData, RxSubscriber<ChiefOrderByCodeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getChiefOrderByCode(requestData)
                .compose(RxHelper.<ChiefOrderByCodeBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 订单团长提货核销 团长使用
     *
     */
    Subscription setFetched(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .setFetched(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 小程序端订单统一查询接口
     */
    Subscription orderList(Map<String, String> requestData, RxSubscriber<OrderListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderList(getRequestBody(requestData))
                .compose(RxHelper.<OrderListBean>handleResult())
                .subscribe(mRxSubscriber);
    }
    /**
     * 获取佣金明细
     */
    Subscription recordList(Map<String, String> requestData, RxSubscriber<CommissionDetailsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .recordList(getRequestBody(requestData))
                .compose(RxHelper.<CommissionDetailsBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 申请提现
     */
    Subscription commissionApply(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .commissionApply(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 创建充值订单
     */
    Subscription orderCreate(Map<String, String> requestData, RxSubscriber<RechargeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderCreate(requestData)
                .compose(RxHelper.<RechargeBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription productList(Map<String, Object> requestData, RxSubscriber<ProductListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .productList(getRequestBodys(requestData))
                .compose(RxHelper.<ProductListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    public RequestBody getRequestBodys(Map<String, Object> hashMap) {
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
