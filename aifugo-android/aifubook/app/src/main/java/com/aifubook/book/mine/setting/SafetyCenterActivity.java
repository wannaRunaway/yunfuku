package com.aifubook.book.mine.setting;

import android.os.Bundle;
import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.jiarui.base.baserx.bean.MessageEvent;

import butterknife.OnClick;

public class SafetyCenterActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_safety_center;
    }

    @Override
    public void initView() {
        setTitle("安全中心");

    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.LOGIN_OUT){
            SafetyCenterActivity.this.finish();
        }
    }

    @OnClick({R.id.tv_logout, R.id.USerManger, R.id.prisitionManger})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                startActivity(LogoutActivity.class);

                break;
            case R.id.USerManger:
                Bundle bundle = new Bundle();
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html");
                bundle.putString("title", "用户协议");
                startActivity(PriviteActivity.class, bundle);
                break;
            case R.id.prisitionManger:
                Bundle bundle1 = new Bundle();
                bundle1.putString("fig", "https://api.aifubook.com/bookstatic/html/privacy-policy-privacy.html");
                bundle1.putString("title", "隐私条款");
                startActivity(PriviteActivity.class, bundle1);
                break;
        }
    }


    @Override
    public void initPresenter() {

    }


}
