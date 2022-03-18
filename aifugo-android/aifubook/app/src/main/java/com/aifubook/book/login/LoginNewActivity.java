package com.aifubook.book.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.SpannableStringUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;
import com.journeyapps.barcodescanner.Util;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginNewActivity extends BaseActivity<LoginPresenter> implements LoginView {


    private static final String TAG = "LoginNewActivity";
    @BindView(R.id.account_edit)
    EditText account_edit;

    @BindView(R.id.emailcode_edit)
    EditText emailcode_edit;

    @BindView(R.id.sendcode_tv)
    TextView sendcode_tv;

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    @BindView(R.id.agree_text)
    TextView agree_text;

    @BindView(R.id.iv_weChat)
    ImageView iv_weChat;

    @BindView(R.id.tv_login)
    TextView tv_login;

    private SpannableStringBuilder spannableStringBuilder;
    private String inviteCode="";

    //TODO 处理扫码接收的

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_new;
    }


    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("");
//        verifyStoragePermissions();
        String text = ContextUtil.getString(this, R.string.register_agreement);
        spannableStringBuilder = SpannableStringUtil.spannableString(this, agree_text, text, 18, 26, 27, text.length());
        agree_text.setText(spannableStringBuilder);

        try {
            inviteCode = getIntent().getExtras().getString("inviteCode");
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @OnClick({R.id.sendcode_tv, R.id.tv_login, R.id.iv_weChat})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.sendcode_tv:

                sendCode();
                break;
            case R.id.tv_login:

                login();
                break;
            case R.id.iv_weChat:
                weChatLogin();
                break;
        }
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.WECHAT_LOGIN) {
            weChatLoginForApp();
        }

    }

    private void weChatLoginForApp() {
        Map<String, Object> map = new HashMap<>();
        map.put("headimgurl", "" + MyApp.headimgurl);
        map.put("nickname", "" + MyApp.nickname);
        map.put("openId", "" + MyApp.openId);
        map.put("unionId", "" + MyApp.unionId);
        map.put("inviteCode", inviteCode+"");
        if (SharedPreferencesUtil.get(this, ShareKey.Companion.getTEACHERORSTUDENT(), ShareKey.Companion.getDefaultTeacherOrStudent()).equals(ShareKey.Companion.getDefaultTeacherOrStudent())){
            map.put("registeredIdentity", 0);//0家长 1教师
        }else {
            map.put("registeredIdentity", 1);
        }
        map.put("grade", SharedPreferencesUtil.get(this, ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade()));
        mPresenter.weChatLoginForApp(map);
    }

    private void weChatLogin() {
        if (!checkBox.isChecked()) {
            showToast("请同意并勾选《用户服务协议》和《隐私政策》");
            return;
        }
        if (!MyApp.mWxApi.isWXAppInstalled()) {
            showToast("您还未安装微信");
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            MyApp.mWxApi.sendReq(req);
        }

    }

    private void login() {

        String phone = account_edit.getText().toString();
        String code = emailcode_edit.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (phone.length()!=11){
            showToast("手机号码仅支持11位");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }
        if (!checkBox.isChecked()) {
            showToast("请同意并勾选《用户服务协议》和《隐私政策》");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("smsCode", code);
        map.put("inviteCode", inviteCode);
        mPresenter.loginNew(map);


    }

    private void sendCode() {

        String phone = account_edit.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("scene", "" + 0);
        mPresenter.sendSmsCode(map);

    }


    @Override
    public void GetverificationCodeSuc(Integer DataBean) {
        ToastUitl.showShort(this, "验证码发送成功!");
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(sendcode_tv);
        TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);
    }

    @Override
    public void GetverificationCodeFail(String Message) {

    }

    @Override
    public void loginSuccess(LoginHomePageBean DataBean) {
        LogUtil.e(TAG, "logo==" + DataBean.getLogo());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.NICKNAME, "" + DataBean.getNickname());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_LOGO, "" + DataBean.getLogo());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.IS_LOGIN, "1");
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.PHONE, "" + DataBean.getMobile());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_ID, "" + DataBean.getId());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.LOGON_TOKEN, "" + DataBean.getToken());
        LogUtil.d(DataBean.getToken()+"token ");
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.MENU_FLAG, DataBean.isMenuFlag());
        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.RECOMMEND_ID, DataBean.getRecommendId()+"");
        SharedPreferencesUtil.put(MyApp.getInstance(), "CityId", "" + DataBean.getCityId());
        MessageEvent event = new MessageEvent(MessageEvent.LOGIN);
        EventBusUtil.post(event);
        ToastUitl.showShort(this, "登录成功!");
        this.finish();
    }


    @Override
    public void UserRegisterSuc(LoginHomePageBean DataBean) {

    }


    @Override
    public void weChatLoginForFail(String DataBean) {
        showToast(DataBean);
    }

    @Override
    public void loginFail(String DataBean) {
        showToast(DataBean);
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {            //检查是否已经给了权限
            int permission = ActivityCompat.checkSelfPermission(MyApp.getInstance(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //没有给权限
                // Log.e("permission","动态申请");                //参数分别是当前活动，权限字符串数组，requestcode
                ActivityCompat.requestPermissions(LoginNewActivity.this, PERMISSIONS_STORAGE, 100);
            } else {
            }
        }
    }


    @Override
    public void showLoading(String title) {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
}
