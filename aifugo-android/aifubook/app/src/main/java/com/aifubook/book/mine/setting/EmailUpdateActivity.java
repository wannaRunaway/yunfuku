package com.aifubook.book.mine.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EmailUpdateActivity extends BaseActivity<PaySettingPresenter> implements PaySettingView {

    @BindView(R.id.et_Phone)
    EditText et_Phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_email_update;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PaySettingPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("绑定邮箱");
    }

    @OnClick({R.id.Nexts})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.Nexts:
                if (StringUtils.isEmpty(et_Phone.getText().toString() + "")) {
                    ToastUitl.showShort(this, "邮箱不能为空!");
                    return;
                }
                updateMemberEmail();
                break;
        }
    }

    // 发送验证码接口
    private void updateMemberEmail() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "" + et_Phone.getText().toString());
        mPresenter.updateMemberEmail(map);
    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {

    }

    @Override
    public void GetverificationCodeFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMemberEmail(String DataBean) {
        this.finish();
        Toast.makeText(mContext, "邮箱修改成功!", Toast.LENGTH_SHORT).show();
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
