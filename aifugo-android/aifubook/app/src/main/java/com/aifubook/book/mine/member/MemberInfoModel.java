package com.aifubook.book.mine.member;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.LogoutBean;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
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

public class MemberInfoModel implements BaseModel {


    /**
     * 获取各种订单数量
     * @param mRxSubscriber
     * @return
     */
    Subscription getOrderCount(RxSubscriber<OrderCountVO> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getOrderCount()
                .compose(RxHelper.<OrderCountVO>handleResult())
                .subscribe(mRxSubscriber);
    }

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
     * 修改用户信息
     */
    Subscription appliedShop(Map<String, String> requestData, RxSubscriber<ShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .appliedShop(requestData)
                .compose(RxHelper.<ShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription shopDetail(Map<String, String> requestData, RxSubscriber<ShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .shopDetail(requestData)
                .compose(RxHelper.<ShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription getMostNearShop(Map<String, Object> requestData, RxSubscriber<NearShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getMostNearShop(requestData)
                .compose(RxHelper.<NearShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription requestGetTip(Map<String, String> requestData, List<File> filelist, RxSubscriber<String> mRxSubscriber) {
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
                .uploadMulti(maps)
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

    /**
     * 团长详情信息
     */
    Subscription chiefDetail(Map<String, String> requestData, RxSubscriber<ChiefDetailsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .chiefDetail(requestData)
                .compose(RxHelper.<ChiefDetailsBean>handleResult())
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
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription productList(Map<String, String> requestData, RxSubscriber<ProductListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .productList(getRequestBody(requestData))
                .compose(RxHelper.<ProductListBean>handleResult())
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
