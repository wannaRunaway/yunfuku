package com.aifubook.book.shoprequest;

import android.util.Log;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
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

public class ShopRequestModel implements BaseModel {


    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription productDetail(Map<String, String> requestData, RxSubscriber<ProductDetailBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .productDetail(requestData)
                .compose(RxHelper.<ProductDetailBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription shopApply(RequestBody requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .shopApply(requestData)
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
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    Subscription productList(Map<String, Object> requestData, RxSubscriber<ProductListBean> mRxSubscriber) {
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
