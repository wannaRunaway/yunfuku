package com.aifubook.book.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.aifubook.book.Constants;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 生成小程序二维码工具类
 * https://blog.csdn.net/cholg/article/details/108006816
 * https://www.jianshu.com/p/9d58df406f2b
 * https://www.jianshu.com/p/b9f28463ab9c
 * https://huangxiaoguo.blog.csdn.net/article/details/89841766
 * https://my.oschina.net/waitforyou/blog/3025393
 * https://blog.csdn.net/qq_33391220/article/details/94638524
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.createQRCode.html
 */
public class MiniCodeUtil {

    private static final String TAG = "HttpRequestUtil";
    private static final int OK = 0x11;
    private static final int ERROR = 0x12;

    //    private Drawable wechatqrcode;
    private Bitmap wechatqrcode;

    private Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OK:
                    requestResponse.onResponse(wechatqrcode);
                    break;
                case ERROR:
                    requestResponse.onFailure();
                    break;
            }
        }
    };


    private RequestResponse requestResponse;

    public interface RequestResponse {
        void onFailure();

        void onResponse(Bitmap wechatqrcode);
    }

    public void setOnRequestListener(RequestResponse response) {
        this.requestResponse = response;
    }

    public void getRegisterBitmapData(String access_token,String inviteCode) {
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
        //String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;

        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                MediaType parse = MediaType.parse("application/json; charset=utf-8");
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("page", "pages/tab-bar/index");
                hashMap.put("scene", "p=" + "&i=" + inviteCode+"&c=");
                JSONObject jsonObject = new JSONObject();
                StringBuffer data = new StringBuffer();
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
                LogUtil.e(TAG, "json==" + jsonObject.toString());
                RequestBody requestBody = RequestBody.create(parse, jsonObject.toString());

                Request request = new Request.Builder().url(url).post(requestBody).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.e(TAG, e.getMessage());
                        mHandler.sendEmptyMessage(ERROR);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.e(TAG, "response=" + response.toString());
                        InputStream inputStream = response.body().byteStream();
                        // BufferedInputStream bis = new BufferedInputStream(response.body().byteStream());
                        //wechatqrcode = Drawable.createFromStream(inputStream, "wechatqrcode");
                        wechatqrcode = BitmapFactory.decodeStream(inputStream);
                        mHandler.sendEmptyMessage(OK);
                    }
                });
            }
        }.start();

    }


    public void getBitmapData(String access_token,String inviteCode, String productId) {
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
        //String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;

        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                MediaType parse = MediaType.parse("application/json; charset=utf-8");
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("page", "pages-product/product-detail/product-detail");
                hashMap.put("scene", "p=" + productId + "&i=" + inviteCode+"&c=");
                JSONObject jsonObject = new JSONObject();
                StringBuffer data = new StringBuffer();
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
                LogUtil.e(TAG, "json==" + jsonObject.toString());
                RequestBody requestBody = RequestBody.create(parse, jsonObject.toString());

                Request request = new Request.Builder().url(url).post(requestBody).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.e(TAG, e.getMessage());
                        mHandler.sendEmptyMessage(ERROR);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.e(TAG, "response=" + response.toString());
                        InputStream inputStream = response.body().byteStream();
                        // BufferedInputStream bis = new BufferedInputStream(response.body().byteStream());
                        //wechatqrcode = Drawable.createFromStream(inputStream, "wechatqrcode");
                        wechatqrcode = BitmapFactory.decodeStream(inputStream);
                        mHandler.sendEmptyMessage(OK);
                    }
                });
            }
        }.start();

    }

    /**
     * 根据小程序APPID和SECRET获取微信小程序token
     *
     * @return
     * @throws JSONException
     */
    public void getWxAccessToken(String inviteCode, String productId) {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.minAPPID + "&secret=" + Constants.minAppSecret;
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String access_token = obj.optString("access_token");

                    String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
                    //String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            OkHttpClient client = new OkHttpClient();

                            MediaType parse = MediaType.parse("application/json; charset=utf-8");
                            Map<String, String> hashMap = new HashMap<>();
                            hashMap.put("page", "pages-product/product-detail/product-detail");
                            hashMap.put("scene", "productId=" + productId + "&inviteCode=" + inviteCode);
                            JSONObject jsonObject = new JSONObject();
                            StringBuffer data = new StringBuffer();
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
                            LogUtil.e(TAG, "json==" + jsonObject.toString());
                            RequestBody requestBody = RequestBody.create(parse, jsonObject.toString());

                            Request request = new Request.Builder().url(url).post(requestBody).build();

                            Call call = client.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    LogUtil.e(TAG, e.getMessage());
                                    mHandler.sendEmptyMessage(ERROR);
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    LogUtil.e(TAG, "response=" + response.toString());
                                    InputStream inputStream = response.body().byteStream();
                                    // BufferedInputStream bis = new BufferedInputStream(response.body().byteStream());
                                    //wechatqrcode = Drawable.createFromStream(inputStream, "wechatqrcode");
                                    wechatqrcode = BitmapFactory.decodeStream(inputStream);
                                    mHandler.sendEmptyMessage(OK);
                                }
                            });
                        }
                    }.start();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OkHttpUtils.get(url, resultCallback);

    }


}
