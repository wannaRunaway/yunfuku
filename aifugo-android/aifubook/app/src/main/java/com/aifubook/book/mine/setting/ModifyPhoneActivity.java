package com.aifubook.book.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.model.ModifyModel;
import com.aifubook.book.model.OnCallBack;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改手机号第一步
 */
public class ModifyPhoneActivity extends BaseActivity implements OnCallBack {


    private static final int GET_SMSCODE = 0x11;
    private static final int CHECK_CODE = 0x12;

    @Override
    protected int setContentView() {
        return R.layout.activity_modify_phone;
    }


    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);

        findView();
        initData();

    }

    private ModifyModel modifyModel;
    private String phone;

    private TextView title_name;
    private ImageView leftImg;
    private void initData() {

//        mHeadView.setCentreTextView("修改手机号");
        title_name = findViewById(R.id.textview);
        leftImg = findViewById(R.id.leftImg);
        title_name.setText("修改手机号");
        leftImg.setOnClickListener(v -> finish());
        phone = getIntent().getStringExtra("phone");
        tv_phone.setText(phone);

        modifyModel = new ModifyModel(new PaySettingModel());
        modifyModel.setOnCallBackListener(this);


    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.CHANGE_PHONE){
            ModifyPhoneActivity.this.finish();
        }
    }

    private TextView tv_phone;
    private EditText et_code;
    private TextView tv_send_code;
    private TextView tv_next;


    private void findView() {

        tv_phone = findViewById(R.id.tv_phone);
        et_code = findViewById(R.id.et_code);
        tv_send_code = findViewById(R.id.tv_send_code);
        tv_next = findViewById(R.id.tv_next);

        tv_send_code.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    private void sendCode() {
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone + "");
        map.put("scene", "3");
        modifyModel.sendMobileCode(map, GET_SMSCODE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_next:
                showLoadingDialog();
                String code = et_code.getText().toString();
                if (!StringUtils.isEmpty(code)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("code", code+"");
                    map.put("mobile",phone+"");
                    modifyModel.checkCode(map, CHECK_CODE);
                } else {
                    ToastUtil.showToast("验证码不能为空", false);
                }
                break;
        }
    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == GET_SMSCODE) {

            WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
            TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);

        } else if (type == CHECK_CODE) {

            Intent intent = new Intent(ModifyPhoneActivity.this,PhoneSettingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onError(String error, int type) {
        closeLoadingDialog();
        ToastUtil.showToast(error,false);
    }
}
