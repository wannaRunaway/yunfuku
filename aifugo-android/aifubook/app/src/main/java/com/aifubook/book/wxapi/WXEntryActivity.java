package com.aifubook.book.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.utils.OkHttpUtils;
import com.aifubook.book.utils.Param;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.bairui.elves.MainActivity;
//import com.bairui.elves.application.MyApp;
//import com.bairui.elves.homepage.NextImageSendActivity;
//import com.bairui.elves.login.LoginByPhoneCodeActivity;
//import com.bairui.elves.login.LoginHomeModel;
//import com.bairui.elves.login.LoginHomePageActivity;
//import com.bairui.elves.login.PhoneRegisterActivity;
//import com.bairui.elves.mine.RechargeActivity;
//import com.bairui.elves.utils.OkHttpUtils;
//import com.bairui.elves.utils.Param;
//import com.bairui.elves.utils.SharedPreferencesUtil;

/**
 * Created by ListKer_Hlg on 2018/10/25
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private final int REQUEST_PAY = 100;
    private String unionid;
    private String openid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        MyApp.mWxApi.handleIntent(getIntent(), this);

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e(TAG, "错误码 : " + resp.errCode + " type=" + resp.getType());
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: {
                switch (resp.getType()) {
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        LogUtil.e(TAG, "登录失败 ");
                        finish();
                        break;
                    case 1:          // 登录
                        String code = ((SendAuth.Resp) resp).code;
                        //获取用户信息
                        getAccessToken(code);
                        finish();
                        break;
                    case 2:          // 分享
//                        ShareId();
                        finish();
                        break;
                    case ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM:
                        WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
                        String extraData =launchMiniProResp.extMsg;
                        LogUtil.e(TAG,"extraData="+extraData);
                        break;
                   /* case ConstantsAPI.COMMAND_PAY_BY_WX:
                        LogUtil.e(TAG, "pay success");
//                        Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
//                        if (PayOrderActivity.payOrderActivity != null) {
//                            PayOrderActivity.payOrderActivity.isStart = true;
//                        }else{
                        //OrderFragment
                        MessageEvent event = new MessageEvent(MessageEvent.ORDER_PAY_SUCCESS);
                        EventBusUtil.post(event);

//                        }


                        WXEntryActivity.this.finish();
                        break;*/
                }
            }
            break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                switch (resp.getType()) {
                    case 1:          // 登录
                        finish();
                        break;
                    case 2:          // 分享
                        Log.e("XXXXXXXXXXX", "取消取消取消取消取消取消");
                        finish();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                switch (resp.getType()) {
                    case 1:          // 登录
                        finish();
                        break;
                    case 2:          // 分享
                        Log.e("XXXXXXXXXXX", "取消取消取消取消取消取消");
                        finish();
                        break;
                }
                break;
            default:
                Log.e("XXXXXXXXXXX", "失败失败失败");
                Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
                finish();
                break;

        }
    }

    private void


    getAccessToken(String code) {
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append("wx494d5354ef916936")
                .append("&secret=")
                .append("1cdaaf68817046af0b650bbcce1baa13")
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取个人信息
                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
                OkHttpUtils.ResultCallback<String> reCallback = new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String responses) {
                        String nickName = null;
                        String headimgurl = null;
                        String openid = null;
                        try {
                            JSONObject jsonObject = new JSONObject(responses);
                            nickName = jsonObject.getString("nickname");
                            headimgurl = jsonObject.getString("headimgurl");
                            openid = jsonObject.getString("openid");
                            unionid = jsonObject.getString("unionid");

                            MyApp.headimgurl = headimgurl;
                            MyApp.nickname = nickName;
                            MyApp.openId = openid;
                            MyApp.unionId = unionid;
                            MyApp.ResumeTime = "2";

                            Log.e("WhatMessage2", "" + MyApp.ResumeTime);

//                            if (LoginActivity.loginActivity != null) {
//                                LoginActivity.loginActivity.ins();
//                            }
                            MessageEvent event = new MessageEvent(MessageEvent.WECHAT_LOGIN);
                            EventBusUtil.post(event);

//                            if (AcountMangerActivity.acountMangerActivity != null) {
//                                AcountMangerActivity.acountMangerActivity.ins();
//                            }


                            WXEntryActivity.this.finish();

//                            SendCode(headimgurl, nickName, openid, unionid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };
                OkHttpUtils.get(getUserInfo, reCallback);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        OkHttpUtils.get(loginUrl.toString(), resultCallback);
    }

//    // 发送验证码接口
//    private void SendCode(String Himg, String nickName, String OpenId, String unionId) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("headimgurl", "" + Himg);
//        map.put("nickname", "" + nickName);
//        map.put("openId", "" + OpenId);
//        map.put("unionId", "" + unionId);
//        mPresenter.weChatLoginForApp(map);
//    }

//    private void SendCode(String Himg, String nickName, String OpenId, String unionId) {
//        String getUserInfo = ApiService.BASE_HOST + "/member/weChatLoginForApp";
//        OkHttpUtils.ResultCallback<String> reCallback = new OkHttpUtils.ResultCallback<String>() {
//            @Override
//            public void onSuccess(String responses) {
//                Log.e("WhatMessage1", "Login" + responses);
////                WXEntryActivity.this.finish();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e("WhatMessage2", "Login" + e);
////                Toast.makeText(WXEntryActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        };
//        List<Param> list = new ArrayList<>();
//        Param param = new Param("headimgurl", "" + Himg);
//        list.add(param);
//        Param params = new Param("nickname", "" + nickName);
//        list.add(params);
//        Param param1 = new Param("openId", "" + OpenId);
//        list.add(param1);
//        Param param2 = new Param("unionId", "" + unionId);
//        list.add(param2);
//
//        OkHttpUtils.post(getUserInfo, reCallback, list);
//    }

//    public void weChatLoginForApp(String DataBean) {
//        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.IS_LOGIN, "1");
////        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.PHONE, "" + DataBean.getUsername());
////        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_ID, "" + DataBean.getId());
////        SharedPreferencesUtil.put(MyApp.getInstance(), "CityId", "" + DataBean.getCityId());
//        ToastUitl.showShort(this, "登录成功!");
//        startActivity(MainActivity.class);
//        this.finish();
//        WXEntryActivity.this.finish();
//    }

}