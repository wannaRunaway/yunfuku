package com.aifubook.book.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aifubook.book.application.MyApp;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private static final String TAG = "WXPayEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果没回调onResp，八成是这句没有写
        MyApp.mWxApi.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApp.mWxApi.handleIntent(intent, this);

    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode + " type==" + resp.getType());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            int code = resp.errCode;
            if (code == 0) {
                LogUtil.e(TAG, "pay success");
                MessageEvent event = new MessageEvent(MessageEvent.ORDER_PAY_SUCCESS);
                EventBusUtil.post(event);

            } else if (code == -1) {
                //支付错误
                LogUtil.d(TAG, resp.errStr);

            } else if (code == -2) {
                //用户取消

                LogUtil.d(TAG, "用户取消支付");
                MessageEvent event = new MessageEvent(MessageEvent.CANCEL_WECHAT_PAY);
                EventBusUtil.post(event);
            } else {
                LogUtil.d(TAG, "支付异常");


            }
            finish();

        } else {

            finish();
        }
    }

    private int count = 0;
    private Handler handler = new Handler();

    private void getPayResult(String top_up_id) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    protected void startResultWeb(String url) {

    }
}
