package com.aifubook.book.mine.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.StringUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PaySettingActivity extends BaseActivity<PaySettingPresenter> implements PaySettingView {

    @BindView(R.id.et_code)
    EditText et_code;


    @BindView(R.id.tv_send_code)
    TextView tv_send_code;

    @BindView(R.id.mPhoneText)
    TextView mPhoneText;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;


    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_psd;
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.CHANGE_PHONE){
            finish();
        }
    }

    private String type;

    @Override
    public void initPresenter() {
        mPresenter = new PaySettingPresenter(this);
    }

    @Override
    public void initView() {
         type = getIntent().getExtras().getString("Type");
        if("1".equals(type)){
            setTitle("修改手机号");
            ll_phone.setVisibility(View.GONE);
        }else if("2".equals(type)){
            setTitle("绑定手机号");
            ll_phone.setVisibility(View.VISIBLE);
        }else{
            setTitle("支付密码");
        }

        mPhoneText.setText("请输入" + SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.PHONE, "") + "的短信验证码");
    }

    @OnClick({R.id.Nexts})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.Nexts:
                checkCode();
                break;
        }
    }

    private String phone;

    /**
     * 发送验证码
     */
    @OnClick({R.id.tv_send_code})
    void tv_send_code() {
        if (!tv_send_code.getText().toString().equals("获取验证码")) {
            return;
        }
         phone = et_phone.getText().toString();
        if(StringUtils.isEmpty(phone)){
            showToast("手机号不能为空");
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
        if(type.equals("1")) {
            map.put("mobile", "" + SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.PHONE, ""));
        }else{
            map.put("mobile",phone);
        }
        mPresenter.sendSmsCode(map);
    }

    // 发送验证码接口
    private void checkCode() {
        Map<String, String> map = new HashMap<>();

        if(type.equals("1")) {
            map.put("mobile", "" + SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.PHONE, ""));
        }else{
            map.put("mobile",phone);
        }
        map.put("code", "" + et_code.getText().toString());
        mPresenter.checkCode(map);
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
            startActivity(PhoneSettingActivity.class);
        } else if("2".equals(type)){
//            startActivity(SettingPayPsdActivity.class);
//            startActivity(PhoneSettingActivity.class);
            showToast("绑定成功");
            PaySettingActivity.this.finish();
        }

    }

    @Override
    public void UserRegisterSuc(String DataBean) {

    }

    @Override
    public void updateMobile(String DataBean) {

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
