package com.aifubook.book.productcar.trueorder;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.productcar.PayOkeyActivity;
import com.aifubook.book.view.PayPwdDialog;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ToastUitl;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/5 20:21
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class PayOrderActivity extends BaseActivity<PayOrderPresenter> implements PayOrderView {

    private static final String TAG = "PayOrderActivity";
    @BindView(R.id.canPayMoney)
    TextView canPayMoney;

    @BindView(R.id.canUseText)
    RadioButton canUseText;

    @BindView(R.id.canUseWeChat)
    RadioButton canUseWeChat;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;

   // public static PayOrderActivity payOrderActivity;

    public boolean isStart = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_order;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PayOrderPresenter(this);
    }

    String order;
    String tof = "0";

    int SelectPayType = 2;
    private String orderId;

    @Override
    public void initView() {
        setTitle("订单支付");
        //payOrderActivity = this;
        tof = getIntent().getExtras().getString("totalFee");
        order = getIntent().getExtras().getString("order");
        orderId=getIntent().getExtras().getString("OrderId") + "";
        canPayMoney.setText("￥" + (Double.parseDouble(tof) / 100));
        getCanUsedBalance();

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.canUseText) {
                    SelectPayType = 1;
                } else {
                    SelectPayType = 2;
                }
            }
        });
    }

    private void getCanUsedBalance() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getCanUsedBalance(map);
    }

    @OnClick({R.id.saveAddress})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.saveAddress:
                if (SelectPayType == 2) {
                    WeChatPay();
                    return;
                }
                if (payPwdDialog == null) {
                    payPwdDialog = new PayPwdDialog(this);
                }
                payPwdDialog.setInputOver(new PayPwdDialog.InputOver() {
                    @Override
                    public void onInputOver(String password) {
                        Map map = new HashMap();
                        map.put("payOrderId", order);
                        map.put("payType", 0);
                        map.put("payPassword", password);
                        mPresenter.orderToPay(map);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                payPwdDialog.show();
                break;
        }
    }

    private void WeChatPay() {
        Map<String, String> map = new HashMap<>();
        map.put("payOrderId", order + "");
        map.put("payType", "5");
        mPresenter.orderToPayWeChat(map);
    }

    private PayPwdDialog payPwdDialog;


    @Override
    public void MemberInfoSuc(MemberInfoBean DataBean) {

    }

    @Override
    public void UpdateMemberInfoSuc(String DataBean) {

    }

    String canUse = "0";

    @Override
    public void GetCanUsedBalanceSuc(String DataBean) {
        canUse = DataBean;
        canUseText.setText("余额支付(可用余额" + canUse + "元)");
    }

    @Override
    public void GetAccountInfoSuc(GetAccountInfoBean DataBean) {

    }

    @Override
    public void HasPayPasswordSuc(String DataBean) {

    }

    @Override
    public void SetPayPasswordSuc(String DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        ToastUitl.showShort(this, Message + "!");
    }

    @Override
    public void orderToPayWeChat(SendRechargeBean Message) {
        toWXPay(Message);
    }

    @Override
    public void OrderToPaySuc(String DataBean) {
        if (SelectPayType == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("Id", "" + order + "");
            bundle.putString("OrderId", "" + getIntent().getExtras().getString("OrderId") + "");
            startActivity(PayOkeyActivity.class, bundle);
            this.finish();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("Id", "" + order + "");
        bundle.putString("OrderId", "" + getIntent().getExtras().getString("OrderId") + "");
        startActivity(PayOkeyActivity.class, bundle);
        this.finish();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    private IWXAPI iwxapi; //微信支付api

    @Override
    protected void onResume() {
        super.onResume();
       /* if (isStart) {
            isStart = false;
            Bundle bundle = new Bundle();
            bundle.putString("Id", "" + order + "");
            bundle.putString("OrderId", "" + getIntent().getExtras().getString("OrderId") + "");
            startActivity(PayOkeyActivity.class, bundle);
            this.finish();
        }*/
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);

        if(event.getMsg_type()==MessageEvent.ORDER_PAY_SUCCESS){
            LogUtil.e(TAG,"支付成功");
            Bundle bundle = new Bundle();
            bundle.putString("Id", "" + order + "");
            bundle.putString("OrderId", "" + orderId);
            startActivity(PayOkeyActivity.class, bundle);
           // finish();
        }
    }

    /**
     * 调起微信支付的方法
     **/
    private void toWXPay(SendRechargeBean notDataBean) {
        iwxapi = WXAPIFactory.createWXAPI(this, "wx494d5354ef916936", true);
        iwxapi.registerApp("wx494d5354ef916936"); //注册appid  appid可以在开发平台获取

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = "wx494d5354ef916936";
                request.partnerId = notDataBean.getPartnerid();
                request.prepayId = notDataBean.getPrepayId();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = notDataBean.getNonceStr();
                request.timeStamp = notDataBean.getTimeStamp();
                request.sign = notDataBean.getSignType();

                iwxapi.sendReq(request);//发送调起微信的请求
            }

//            private String appId;
//            private String timeStamp;
//            private String nonceStr;
//            private String prepayId;
//            private String signType;
//            private String paySign;
//            private String packageX;

        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
