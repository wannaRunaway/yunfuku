package com.aifubook.book.mine.setting;

import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.utils.PasswordUnderLineEditText;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SettingPayPsdActivity extends BaseActivity<PaySettingPresenter> implements PaySettingView {

    @BindView(R.id.oldPsd)
    PasswordUnderLineEditText oldPsd;

    @BindView(R.id.oldNewPsd)
    PasswordUnderLineEditText oldNewPsd;

    @BindView(R.id.truePsd)
    TextView truePsd;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_pay_psd;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PaySettingPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("支付密码");
        truePsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(oldPsd.getText().toString())) {
                    return;
                }
                if (StringUtils.isEmpty(oldNewPsd.getText().toString())) {
                    return;
                }
                setPayPassword();
            }
        });
    }

    // 发送验证码接口
    private void setPayPassword() {
        Map<String, String> map = new HashMap<>();
        map.put("proPassword", "" + oldPsd.getText().toString());
        map.put("password", "" + oldNewPsd.getText().toString());
        mPresenter.setPayPassword(map);
    }


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
        this.finish();
        ToastUitl.showShort(this, "设置支付密码成功!");
    }

    @Override
    public void updateMobile(String DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        ToastUitl.showShort(this, Message + "!");
    }

    @Override
    public void appBindWeChat(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
