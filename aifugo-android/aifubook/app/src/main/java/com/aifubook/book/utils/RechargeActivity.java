//package com.bairui.waibao.utils;
//
//import android.os.Handler;
//import android.os.Message;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//import com.bairui.elves.R;
//import com.bairui.elves.homepage.NextImageSendActivity;
//import com.bairui.elves.login.LoginHomeModel;
//import com.bairui.elves.mine.bean.PayResult;
//import com.bairui.elves.mine.bean.RechargeBean;
//import com.bairui.elves.mine.bean.SendRechargeBean;
//import com.bairui.elves.utils.SharedPreferencesUtil;
//import com.jiarui.base.bases.BaseActivity;
//import com.jiarui.base.promptlibrary.PromptDialog;
//import com.jiarui.base.utils.StringUtils;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by ListKer_Hlg on 2018/10/12
// * 此类是作用于: 我的模块充值页面
// * 邮箱: 1425034784@qq.com
// */
//
//public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeView {
//
//    @BindView(R.id.Radio_Recharge_Group)
//    RadioGroup Radio_Recharge_Group;
//
//    String RechargeType = "2";
//    String recordIds = "";
//
//    @BindView(R.id.RechargeMoney)
//    TextView RechargeMoney;
//
//    @BindView(R.id.RechargeMoney_TextView)
//    TextView RechargeMoney_TextView;
//
//    @BindView(R.id.RechargeMoney_EditText)
//    EditText RechargeMoney_EditText;
//
//    public static RechargeActivity rechargeActivity;
//
//    private static final int SDK_PAY_FLAG = 1;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_recharge;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter = new RechargePresenter(this);
//    }
//
//    @Override
//    public void initView() {
//        promptDialog = new PromptDialog(this);
//        setTitle("充值");
//        RechargeMoney.setText(getIntent().getExtras().getString("AllMoney") + "");
//        rechargeActivity = RechargeActivity.this;
//
//        RechargeMoney_EditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                RechargeMoney_TextView.setText(charSequence + "元");
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        Radio_Recharge_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                if (i == R.id.Radio_Recharge_wechat) {
//                    RechargeType = "2";
//                } else {
//                    RechargeType = "1";
//                }
//            }
//        });
//    }
//
//    @OnClick({R.id.mTextViewNextOnclick})
//    void Onclicks(View view) {
//        switch (view.getId()) {
//            case R.id.mTextViewNextOnclick:
//                if (StringUtils.isEmpty(RechargeMoney_EditText.getText().toString())) {
//                    return;
//                }
//                SendCreateRecord();
//                break;
//        }
//    }
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 创建充值记录
//     * 简述:
//     * 时间: 2018/10/25
//     */
//    private void SendCreateRecord() {
//        Map<String, String> map = new HashMap<>();
//        map.put("memberId", "" + SharedPreferencesUtil.get(RechargeActivity.this, LoginHomeModel.MEMBERID, ""));
//        map.put("amount", "" + RechargeMoney_EditText.getText().toString());
//        map.put("chargeType", "" + 1);
//        mPresenter.getRecharge(map);
//    }
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 创建充值记录
//     * 简述:
//     * 时间: 2018/10/25
//     */
//    private void PredictCreateRecord() {
//        Map<String, String> map = new HashMap<>();
//        map.put("recordIds", "" + recordIds);
//        map.put("payType", "" + RechargeType);
//        mPresenter.getSendRecharge(map);
//    }
//
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 下面是各个接口的网络回调
//     * 简述:
//     * 时间: 2018/10/25
//     */
//    @Override
//    public void showLoading(String title) {
//        promptDialog.showLoading(title, false);
//    }
//
//    @Override
//    public void stopLoading() {
//        promptDialog.dismiss();
//    }
//
//    @Override
//    public void GetLoginSuc(RechargeBean notDataBean) {
//        recordIds = notDataBean.getRecordIds();
//        PredictCreateRecord();
//    }
//
//    @Override
//    public void GetLoginFail(String Message) {
//
//    }
//
//    @Override
//    public void GetRechargeSuc(SendRechargeBean notDataBean) {
//        if (RechargeType.equals("2")) {
//            toWXPay(notDataBean);
//        } else {
//            RechargeAlipay(notDataBean.getAlipayResult() + "");
//        }
//    }
//
//    @Override
//    public void GetRechargeFail(String Message) {
//
//    }
//
//    private void RechargeAlipay(String alipayResult) {
//        //订单签名后的信息（服务器返回的数据）
//        final String orderInfo = alipayResult;   // 订单信息
//
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(RechargeActivity.this);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        RechargeActivity.this.finish();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//            }
//        }
//    };
//
//
//    private IWXAPI iwxapi; //微信支付api
//
//    /**
//     * 调起微信支付的方法
//     **/
//    private void toWXPay(SendRechargeBean notDataBean) {
//        iwxapi = WXAPIFactory.createWXAPI(this, ""); //初始化微信api
//        iwxapi.registerApp("wxe5beb9faf7efebd1"); //注册appid  appid可以在开发平台获取
//
//        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
//            @Override
//            public void run() {
//                PayReq request = new PayReq(); //调起微信APP的对象
//                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
//                request.appId = "wxe5beb9faf7efebd1";
//                request.partnerId = notDataBean.getParterId();
//                request.prepayId = notDataBean.getPrepayId();
//                request.packageValue = "Sign=WXPay";
//                request.nonceStr = notDataBean.getNonceStr();
//                request.timeStamp = notDataBean.getTimeStamp();
//                request.sign = notDataBean.getPaySign();
//                iwxapi.sendReq(request);//发送调起微信的请求
//            }
//        };
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//}
