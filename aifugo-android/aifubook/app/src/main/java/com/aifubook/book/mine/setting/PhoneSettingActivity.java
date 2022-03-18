package com.aifubook.book.mine.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
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

public class PhoneSettingActivity extends BaseActivity<PaySettingPresenter> implements PaySettingView {

    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.et_Phone)
    EditText et_Phone;

    @BindView(R.id.tv_send_code)
    TextView tv_send_code;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.Nexts)
    TextView Nexts;


    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_update;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PaySettingPresenter(this);
    }

    private String type;

    @Override
    public void initView() {
        setTitle("修改手机号");
        et_Phone.setHint("请输入新手机号");

      /*  type = getIntent().getStringExtra("Type");
        if (!StringUtils.isEmpty(type)) {
            if ("1".equals(type)) {
                setTitle("修改手机号");
                tv_phone.setText("新手机号");
                Nexts.setText("下一步");

            } else if ("2".equals(type)) {
                setTitle("绑定手机号");
                tv_phone.setText("手机号");
                Nexts.setText("绑定");

            }


        }*/


    }

    private String phone;

    @OnClick({R.id.Nexts})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.Nexts:
                if (StringUtils.isEmpty(et_code.getText().toString() + "")) {
                    ToastUitl.showShort(this, "验证码不能为空!");
                    return;
                }
                phone=et_Phone.getText().toString();
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
        map.put("scene","3");
        mPresenter.sendSmsCode(map);
    }

    // 发送验证码接口
    private void updateMobile() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "" + et_Phone.getText().toString() + "");
        map.put("code", "" + et_code.getText().toString());
        mPresenter.updateMobile(map);
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
        if (getIntent().getExtras().getString("Type").equals("1")) {

        } else {
//            startActivity(SettingPayPsdActivity.class);
        }

    }

    @Override
    public void UserRegisterSuc(String DataBean) {

    }

    @Override
    public void updateMobile(String DataBean) {
        this.finish();
        SharedPreferencesUtil.put(PhoneSettingActivity.this,KeyCom.PHONE,phone);

        MessageEvent event = new MessageEvent(MessageEvent.CHANGE_PHONE);
        EventBusUtil.post(event);
        Toast.makeText(mContext, "手机号修改成功!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
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
