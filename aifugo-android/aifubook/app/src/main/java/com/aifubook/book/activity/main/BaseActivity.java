package com.aifubook.book.activity.main;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.OpenActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.StatusBarCompat;
import com.aifubook.book.utils.UmenStatisticsUtil;
import com.jiarui.base.R;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.bases.AppManager;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.promptlibrary.PromptDialog;
import com.jiarui.base.status.ImmersionBar;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.SnackbarUtils;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.views.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @param <P>
 * @author ListKer
 */
public abstract class BaseActivity<P extends BasePresenter<?, ?>> extends FragmentActivity {
    private static final String TAG ="BaseActivity";
    public P mPresenter;
    public Context mContext;
    private Unbinder unbinder;
    //返回
    protected LinearLayout iv_left;
    //标题
    protected TextView tv_title;
    //右边文字
    protected TextView tv_right;
    //左边文字
    protected TextView tv_left;

    //右边图片
    protected LinearLayout lr_right;
    protected ImageView iv_right;
    protected ImmersionBar mImmersionBar;

    private InputMethodManager imm;
    protected PromptDialog promptDialog;
    protected Bundle savedInstanceStates;
    protected View layoutView;
    protected LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        setContentView(layoutView = mInflater.inflate(getLayoutId(), null));
//        this.initSwipeBackHelper();
        if (!(this instanceof MainActivity) && !(this instanceof OpenActivity)) {
            windowTransitionRightInRightOut();
        }
        unbinder = ButterKnife.bind(this);
        EventBusUtil.register(this);

        this.initPresenter();
        //创建对象
        promptDialog = new PromptDialog(this);
        mContext = this;
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
//        initImmersionBar();
//        FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this, true);
        StatusBarCompat.translucentStatusBar(this);

        doBeforeSetcontentView();
        this.initView();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMsg_type() == MessageEvent.SEND_CART) {
            if (!(this instanceof MainActivity)) {
                this.finish();
            }
        }else if(event.getMsg_type() == MessageEvent.BACK_MAIN){
            if(!(this instanceof MainActivity)){
                this.finish();
            }
        }

    }

    private int currentWindowTransitionMode = -1;
    private static final int WINDOW_EXIT_ANIM_MODE_RIGHT_OUT = 0x110;
    private static final int WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT = 0x111;

    protected void windowTransitionRightInRightOut() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        currentWindowTransitionMode = WINDOW_EXIT_ANIM_MODE_RIGHT_OUT;
    }

    protected void windowTransitionBottomInBottomOut() {
        overridePendingTransition(R.anim.slide_down_in, R.anim.activity_stay);
        currentWindowTransitionMode = WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UmenStatisticsUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        UmenStatisticsUtil.onPause(this);
    }

    private long lastClickTime = 0l;
    public static final long DEFAULT_TIME = 500;

    /**
     * true是未登录
     *
     * @return
     */
    public boolean isLogin() {
        if (SharedPreferencesUtil.get(MyApp.getInstance().getApplicationContext(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.e(TAG, "ACTION_DOWN");
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= DEFAULT_TIME;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    /**
     * 初始化状态栏颜色
     */
    protected void initImmersionBar() {
        //初始化状态颜色
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(false, 0f);
//        mImmersionBar.statusBarColor(R.color.white, 0.5f);
//        mImmersionBar.statusBarDarkFont(true, 0.5f);
        mImmersionBar.init();
    }

    /**
     * 初始化SwipeBack
     *//*
    private void initSwipeBackHelper() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(true)// 设置滑动返回开启
                .setSwipeEdgePercent(0.1f).setSwipeSensitivity(0.5f)// 灵敏度
                .setSwipeRelateEnable(true)// 开启滑动视差
                .setSwipeRelateOffset(100);// 视差间隔100
    }

    *//**
     * 禁用滑动返回
     *//*
    public void disableSwipeBack() {
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//       // SwipeBackHelper.onPostCreate(this);
//    }*/


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 通过class跳转的界面
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);

    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        // overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
        if (currentWindowTransitionMode == WINDOW_EXIT_ANIM_MODE_RIGHT_OUT) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
//        else if (currentWindowTransitionMode == WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT) {
//            overridePendingTransition(0, R.anim.slide_down_out);
//        }
    }

    @Override
    public void onBackPressed() {
        if (promptDialog.onBackPressed()) {
            super.onBackPressed();
        } else {
            promptDialog.dismiss();
        }
    }


    /**
     * 设置layout配置
     */
    private void doBeforeSetcontentView() {
        // 把Activity加到Application栈中
        AppManager.getAppManager().addActivity(this);
        // 设置横竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 公共头部
     */
    public void setTitle(@NonNull String title) {
        iv_left = $(R.id.iv_left);
        tv_title = $(R.id.tv_title);
        tv_right = $(R.id.tv_right);
        lr_right = $(R.id.lr_right);
        iv_right = $(R.id.iv_right);
        tv_left = $(R.id.tv_left);
        if (!StringUtils.isEmpty(title))
            tv_title.setText(title);
        //返回
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 子类实现
    public abstract int getLayoutId();

    // 简单的页面无需实现mvp就不管此方法,完美兼容各个实际场景的变通
    public abstract void initPresenter();

    // 初始化视图
    public abstract void initView();


    /**
     * 短时间吐司
     *
     * @param view       当前View
     * @param viewBellow 在哪个View下
     * @param message    信息
     */
    public void showSnackbarShort(View view, View viewBellow, String message) {
        int total = 0;
        //计算R.id.Content离屏幕顶端的距离
        //在Activity中可以直接获取R.id.Content离屏幕顶端的距离
        int[] l2 = new int[2];
        getWindow().findViewById(android.R.id.content).getLocationInWindow(l2);
        total = l2[1];
        SnackbarUtils.Short(view, message).backColor(ContextCompat.getColor(this, R.color.gray3)).bellow(viewBellow, total, 0, 0).show();
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * onDestory所做的操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
        EventBusUtil.unregister(this);
        this.imm = null;
//        if (mImmersionBar != null)
//            mImmersionBar.destroy();
//        SwipeBackHelper.onDestroy(this);
        //解除注解
        this.unbinder = null;
        this.mPresenter = null;
        AppManager.getAppManager().finishActivity(this);
    }

}
