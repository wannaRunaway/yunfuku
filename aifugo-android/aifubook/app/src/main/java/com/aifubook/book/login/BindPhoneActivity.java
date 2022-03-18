package com.aifubook.book.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.mine.setting.SettingPayPsdActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity<BindPhonePresenter> implements BindPhoneView {

    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.et_Phone)
    EditText et_Phone;

    @BindView(R.id.tv_send_code)
    TextView tv_send_code;


    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_update;
    }

    @Override
    public void initPresenter() {
        mPresenter = new BindPhonePresenter(this);
    }

    @Override
    public void initView() {
        setTitle("绑定手机号");
    }

    @OnClick({R.id.Nexts})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.Nexts:
                if (StringUtils.isEmpty(et_code.getText().toString() + "")) {
                    ToastUitl.showShort(this, "验证码不能为空!");
                    return;
                }
                if (StringUtils.isEmpty(et_Phone.getText().toString() + "")) {
                    ToastUitl.showShort(this, "手机号不能为空!");
                    return;
                }
                updateMobile();
                break;
        }
    }

    /**
     * 发送验证码
     */
    @OnClick({R.id.tv_send_code})
    void tv_send_code() {
        if (!tv_send_code.getText().toString().equals("获取验证码")) {
            return;
        }
        if (StringUtils.isEmpty(et_Phone.getText().toString() + "")) {
            ToastUitl.showShort(this, "手机号不能为空!");
            return;
        }
        sedTel();
    }

    /**
     * 发送验证码
     */
    private void sedTel() {
        SendCode();
    }

    // 发送验证码接口
    private void SendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "" + et_Phone.getText().toString() + "");
        map.put("scene", "0");
        mPresenter.sendSmsCodeTrue(map);
    }

    // 发送验证码接口
    private void updateMobile() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "" + et_Phone.getText().toString() + "");
        map.put("smsCode", "" + et_code.getText().toString());
        mPresenter.bindMobile(map);
    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {
        ToastUitl.showShort(this, "验证码发送成功!");
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
        TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);
    }

    @Override
    public void GetverificationCodeFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMemberEmail(String DataBean) {

    }

    @Override
    public void GetHomePageSuc(String DataBean) {
        this.finish();
//        if (getIntent().getExtras().getString("Type").equals("1")) {
//
//        } else {
//            startActivity(SettingPayPsdActivity.class);
//        }

    }

    @Override
    public void UserRegisterSuc(String DataBean) {

    }

    @Override
    public void updateMobile(String DataBean) {
        this.finish();
        Toast.makeText(mContext, "手机号码修改成功!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void bindMobile(LoginHomePageBean DataBean) {
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.IS_LOGIN, "1");
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.PHONE, "" + DataBean.getMobile());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_ID, "" + DataBean.getId());
        SharedPreferencesUtil.put(MyApp.getInstance(), "CityId", "" + DataBean.getCityId());
        ToastUitl.showShort(this, "绑定成功!");
        MessageEvent event = new MessageEvent(MessageEvent.CHANGE_PHONE);
        EventBusUtil.post(event);
        this.finish();
    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
