package com.aifubook.book.mine.setting;

import android.os.Bundle;
import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.mine.self.PersonalUpdataActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.LOGIN_OUT){
            SettingActivity.this.finish();
        }
    }

    @Override
    public void initView() {
        setTitle("设置");
    }

    @OnClick({R.id.safetyCenter,R.id.loginOut, R.id.clear, R.id.SelfSManger, R.id.PayManger, R.id.AcountManger, R.id.MessageManger, R.id.USerManger, R.id.prisitionManger})
    void Onclicks(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.loginOut:
                MyApp.ResumeTime = "1";
                SharedPreferencesUtil.clear(this);
                MessageEvent event = new MessageEvent(MessageEvent.LOGIN_OUT);
                EventBusUtil.post(event);
                SettingActivity.this.finish();
                //startActivity(LoginNewActivity.class);
                break;
            case R.id.clear:
                break;
            case R.id.safetyCenter:
                startActivity(SafetyCenterActivity.class);
                break;
            case R.id.SelfSManger:
                startActivity(PersonalUpdataActivity.class);
//                startActivity(PersonalActivity.class);
                break;
            case R.id.PayManger:  // 修改支付密碼
                Bundle b = new Bundle();
                b.putString("Type", "2");
                startActivity(PaySettingActivity.class, b);
                break;
            case R.id.AcountManger:
                startActivity(AcountMangerActivity.class);
                break;
            case R.id.MessageManger:
                startActivity(BackMessageActivity.class);
                break;
            case R.id.USerManger:
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html");
                bundle.putString("title", "用户协议");
                startActivity(PriviteActivity.class, bundle);
                break;
            case R.id.prisitionManger:
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/privacy-policy-privacy.html");
                bundle.putString("title", "隐私条款");
                startActivity(PriviteActivity.class, bundle);
                break;


        }
    }

}
