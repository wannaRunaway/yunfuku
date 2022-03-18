package com.aifubook.book.mine.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.BindPhoneActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AcountMangerActivity extends BaseActivity<PaySettingPresenter> implements PaySettingView {

    @BindView(R.id.phoneUpDate)
    TextView phoneUpDate;

    @BindView(R.id.isBindWechat)
    TextView isBindWechat;

    @BindView(R.id.linear)
    LinearLayout linear;


    @Override

    public int getLayoutId() {
        return R.layout.acitivity_acount_manger;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PaySettingPresenter(this);
    }

    IWXAPI iwxapi;

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.CHANGE_PHONE) {
            AcountMangerActivity.this.finish();
        } else if(event.getMsg_type() == MessageEvent.WECHAT_LOGIN){
            appBindWeChat();
        }
    }

    private String phone;

    @Override
    public void initView() {
        setTitle("账号管理");


        iwxapi = WXAPIFactory.createWXAPI(this, "wx494d5354ef916936", true);
        iwxapi.registerApp("wx494d5354ef916936");

        int is = SharedPreferencesUtil.get(MyApp.getInstance(), "isBindWeChat", 0);
        phone = SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.PHONE, "") + "";

        emailTextView.setText(SharedPreferencesUtil.get(this, "email", "") + "");


        if (is == 0) {
            isBindWechat.setText("未绑定");
        } else {
            isBindWechat.setText("已绑定");
        }


        phoneUpDate.setText(SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.PHONE, "") + "");

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                if (StringUtils.isEmpty(phone)) {
//                    b.putString("Type", "2");
                    startActivity(BindPhoneActivity.class, b);
                } else {
//                    b.putString("Type", "1");
//                    startActivity(PhoneSettingActivity.class, b);
                    b.putString("phone", phone);
                    startActivity(ModifyPhoneActivity.class, b);
                }
//                startActivity(PaySettingActivity.class, b);
            }
        });

        emailUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EmailUpdateActivity.class);
            }
        });

        WechatlUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!iwxapi.isWXAppInstalled()) {
                    ToastUitl.showShort(AcountMangerActivity.this, "您还未安装微信");
                } else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    iwxapi.sendReq(req);
                }
            }
        });
    }

    public void ins() {
        appBindWeChat();
    }


    private void appBindWeChat() {
        Map<String, String> map = new HashMap<>();
        map.put("headimgurl", "" + MyApp.headimgurl);
        map.put("nickname", "" + MyApp.nickname);
        map.put("openId", "" + MyApp.openId);
        map.put("unionId", "" + MyApp.unionId);

        if (mPresenter == null) {
            return;
        }

        mPresenter.appBindWeChat(map);
    }


    @BindView(R.id.WechatlUpDate)
    LinearLayout WechatlUpDate;

    @BindView(R.id.emailUpDate)
    LinearLayout emailUpDate;

    @BindView(R.id.emailTextView)
    TextView emailTextView;

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {

    }

    @Override
    public void GetverificationCodeFail(String Message) {

    }

    @Override
    public void updateMemberEmail(String DataBean) {

    }

    @Override
    public void GetHomePageSuc(String DataBean) {

    }

    @Override
    public void UserRegisterSuc(String DataBean) {

    }

    @Override
    public void updateMobile(String DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        ToastUitl.showLong(this, Message + "!");
    }

    @Override
    public void appBindWeChat(String Message) {
        ToastUitl.showLong(this, "绑定成功!");
        isBindWechat.setText("已绑定");
        SharedPreferencesUtil.put(AcountMangerActivity.this, "isBindWeChat", 1);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
