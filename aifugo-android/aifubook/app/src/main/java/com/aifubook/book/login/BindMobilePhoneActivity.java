package com.aifubook.book.login;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.activity.main.MainActivity;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class BindMobilePhoneActivity extends BaseActivity {

    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send_code)
    TextView tv_send_code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_mobile_phone;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("");
    }


    @OnClick(R.id.tv_send_code)
    void tv_send_code() {//发送验证码
        if (StringUtils.isMobileNO(et_account.getText().toString())) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
        TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);
        sedTel();
    }

    /**
     * 发送验证码
     */
    private void sedTel() {

    }

    /**
     * 确认
     */
    @OnClick(R.id.tv_confirm)
    void tv_confirm(){
        startActivity(MainActivity.class);
    }

}