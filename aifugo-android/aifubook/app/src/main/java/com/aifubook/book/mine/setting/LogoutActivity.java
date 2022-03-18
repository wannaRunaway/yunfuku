package com.aifubook.book.mine.setting;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.mine.member.LogoutView;
import com.aifubook.book.mine.self.LogoutPresenter;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.SpannableStringUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;

import butterknife.BindView;

public class LogoutActivity extends BaseActivity<LogoutPresenter> implements LogoutView {

    private SpannableStringBuilder spannableStringBuilder;
    private static final String TAG = "LogoutActivity";

    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.tv_logout)
    TextView tv_logout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_logout;
    }

    @Override
    public void initPresenter() {
        mPresenter = new LogoutPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("账号中心");

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowReportDialog dialog = new ShowReportDialog();
                dialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        //logout();
                        mPresenter.logout();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialog.showLogoutDialog(LogoutActivity.this);
            }
        });


        String text = ContextUtil.getString(this, R.string.logout_agreement);
        spannableStringBuilder = SpannableStringUtil.spannableString2(this, text, tv_content, 23, 29);
        tv_content.setText(spannableStringBuilder);
    }


    private void logout() {

      /*  String url = ApiService.BASE_HOST + "member/logoff";

        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                stopProgressDialog();
                LogUtil.e(TAG, "response" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean ok = obj.optBoolean("ok");
                    if (ok) {
                        showToast("注销成功");
                        MyApp.ResumeTime = "1";
                        SharedPreferencesUtil.clear(LogoutActivity.this);
                        MessageEvent event = new MessageEvent(MessageEvent.LOGIN_OUT);
                        EventBusUtil.post(event);
                        LogoutActivity.this.finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                LogUtil.e(TAG, "exception=" + e.toString());

            }
        };

        List<Param> params = new ArrayList<>();

        OkHttpUtils.post(url, resultCallback, params);*/
    }


    @Override
    public void setLogout() {

    }

    @Override
    public void getLogoutResult(String logoutBean) {
        LogUtil.e(TAG,"bbb==");
//        if (logoutBean.isOk()) {
            showToast("注销成功");
            MyApp.ResumeTime = "1";
            SharedPreferencesUtil.clear(LogoutActivity.this);
            MessageEvent event = new MessageEvent(MessageEvent.LOGIN_OUT);
            EventBusUtil.post(event);
            LogoutActivity.this.finish();
//
//        }
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
