package com.aifubook.book.type;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.HomeCategoryBean;
import com.aifubook.book.bean.MyChileBean;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.baserx.bean.BaseBean;
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

public class TypeModel implements BaseModel {


    public Subscription myChief(Map<String, String> requestData, RxSubscriber<MyChileBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .myChief(requestData)
                .compose(RxHelper.<MyChileBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    public Subscription getHomeCategory(RxSubscriber<List<HomeCategoryBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getHomeCategory()
                .compose(RxHelper.<List<HomeCategoryBean>>handleResult())
                .subscribe(mRxSubscriber);
    }


    public Subscription bindChief(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .bindChief(requestData)
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
    public Subscription categoryAll(Map<String, String> requestData, RxSubscriber<List<TypeBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .categoryAll(requestData)
                .compose(RxHelper.<List<TypeBean>>handleResult())
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
     * 根据shopId和经纬度返回数据 无用的接口
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription getMostNearShop(Map<String, Object> requestData, RxSubscriber<NearShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getMostNearShop(requestData)
                .compose(RxHelper.<NearShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 首页轮播图
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription getByScene(Map<String, Object> requestData, RxSubscriber<SceneBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getByScene(requestData)
                .compose(RxHelper.<SceneBean>handleResult())
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
