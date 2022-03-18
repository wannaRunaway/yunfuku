package com.aifubook.book.mine.order;

import android.util.Log;

import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.api.Api;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
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

public class OrderModel implements BaseModel {


    /**
     * 上传快递单号
     * @param requestData
     * @param mRxSubscriber
     * @return
     */
    public Subscription uploadRefundNo(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .uploadRefundNo(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 小程序端订单统一查询接口
     */
    public Subscription orderList(Map<String, String> requestData, RxSubscriber<OrderListBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderList(getRequestBody(requestData))
                .compose(RxHelper.<OrderListBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 创建订单
     */
    Subscription createOrder(Map<String, String> requestData, RxSubscriber<CreateOrderBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .createOrder(getRequestBody(requestData))
                .compose(RxHelper.<CreateOrderBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 订单页面  再次购买
     *
     * @param requestData
     * @param mRxSubscriber
     * @return
     */
    public Subscription reBuy(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .reBuy(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    Subscription carAdd(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .carAdd(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 查询物流
     * */
    Subscription logisticsget(Map<String, String> requestData, RxSubscriber<LogisticsAllBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .logistics(requestData)
                .compose(RxHelper.<LogisticsAllBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 达达查询物流
     * */
    Subscription dadaget(Map<String, String> requestData, RxSubscriber<List<DadaResultBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getdadarecordInfo(requestData)
                .compose(RxHelper.<List<DadaResultBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 未支付的订单重新支付
     */
    public  Subscription orderWXRepay(Map<String, String> requestData, RxSubscriber<SendRechargeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderWXRepay(getRequestBody(requestData))
                .compose(RxHelper.<SendRechargeBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 订单详情 (物理订单 非支付订单)
     */
    public Subscription orderDetail(Map<String, String> requestData, RxSubscriber<OrderDetailsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderDetail(requestData)
                .compose(RxHelper.<OrderDetailsBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 售后服务
     * */
    public Subscription orderService(Map<String, String> requestData, RxSubscriber<ServiceDetailsBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .servicedetails(requestData)
                .compose(RxHelper.<ServiceDetailsBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 撤销申请
     * */
    public Subscription cancelrefund(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .cancelrefund(getRequestBody(requestData))
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 公司列表
     * */
    public Subscription companylist(Map<String, String> requestData, RxSubscriber<List<CompantItem>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .companylist(requestData)
                .compose(RxHelper.handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 订单确认收货并完成
     */
    public Subscription orderCompleted(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderCompleted(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 取消订单
     */
    public Subscription orderSetCancle(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderSetCancle(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 删除订单
     */
    public Subscription orderDelete(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .orderDelete(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获取退款原因枚举定义
     */
    Subscription getRefundReasons(Map<String, String> requestData, RxSubscriber<List<RefundReasonsBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getRefundReasons(requestData)
                .compose(RxHelper.<List<RefundReasonsBean>>handleResult())
                .subscribe(mRxSubscriber);
    }



    /**
     * 退款接口
     */
    Subscription refund(RequestBody requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .refund(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 订单团长提货核销 团长使用
     */
    public Subscription setFetched(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .setFetched(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 查询最大可退款数额
     * */
    public Subscription getmaxrefundfee(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getrefundmaxfee(requestData)
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
                .uploadImage(requestData, maps)
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
