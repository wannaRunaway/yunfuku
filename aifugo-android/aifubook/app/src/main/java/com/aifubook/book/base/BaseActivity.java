package com.aifubook.book.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.groupon.GrouponActivity;
import com.aifubook.book.groupon.GrouponOrderListActivity;
import com.aifubook.book.groupon.GrouponPaySuccessActivity;
import com.aifubook.book.groupon.GrouponShareOrderActivity;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.mine.setting.ModifyPhoneActivity;
import com.aifubook.book.product.ProductDetailsNewActivity;
import com.aifubook.book.productcar.ConfirmOrderActivity;
import com.aifubook.book.shop.ShopCartActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.StatusBarCompat;
import com.aifubook.book.utils.StatusBarUtil;
import com.jiarui.base.appcommon.GlobalAppComponent;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.views.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/*
 * 1、发现了headview,而且很多界面都有这个，在后续注意
 * */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseActivity";
    protected FragmentActivity mActivity;
    protected MyApp mContext;

    protected LayoutInflater mInflater;
    protected View layout_view;

    protected int page;

    private long lastClickTime = 0l;
    public static final long DEFAULT_TIME = 500;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        page = 1;
        mActivity = this;
        mContext = (MyApp) mActivity.getApplicationContext();

        mInflater = LayoutInflater.from(mActivity);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        // 设置屏幕为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GlobalAppComponent.isAutoForwardLogin = true;

        EventBusUtil.register(this);
        onInit(bundle);
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

    public void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
     * 加载布局文件
     */
    protected abstract int setContentView();

    /*
     * 每个activity的onCreate()方法的替代品，对view的初始化操作在该方法里执行
     */
    protected void onInit(Bundle bundle) {
        setContentView(layout_view = mInflater.inflate(setContentView(), null));
        layout_view.setFitsSystemWindows(true);

        if ((this instanceof ProductDetailsNewActivity) | (this instanceof GrouponOrderListActivity)
                | this instanceof ConfirmOrderActivity | this instanceof ShopCartActivity | this instanceof GrouponActivity
                | this instanceof ModifyPhoneActivity | this instanceof GrouponPaySuccessActivity | this instanceof GrouponShareOrderActivity
    ) {
            StatusBarCompat.translucentStatusBar(this);
            StatusBarUtil.setStatusTextColor(true, mActivity);
        } else {
            StatusBarUtil.setStatusTextColor(false, mActivity);
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.red));
        }
        windowTransitionRightInRightOut();
    }

    /*
     * 数据请求，建议重载保持代码风格统一
     */
    protected void loadData() {
    }

    public void onResume() {
        super.onResume();
//        UmenStatisticsUtil.onResume(mActivity);
    }

    public void onPause() {
        super.onPause();
//        UmenStatisticsUtil.onPause(mActivity);
    }

    /**
     * true是未登录
     *
     * @return
     */
    public boolean isLogin() {
        if (SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        // overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //将事件传递给Fragment
        FragmentManager fm = getSupportFragmentManager();
        handlerFragmentOnActivityResult(fm, requestCode, resultCode, data);
    }

    public void handlerFragmentOnActivityResult(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        if (null == fm)
            return;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
                FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                List<Fragment> childFragments = childFragmentManager.getFragments();
                if (childFragmentManager != null) {

                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.leftImg) {
            onBackPressed();
        }

    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }


    private LoadingDialog mLoadingDialog;

    protected void showLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(mActivity);
        }
        mLoadingDialog.showLoadingDialog();
    }

    protected void closeLoadingDialog() {
        if (null != mLoadingDialog) {
            mLoadingDialog.closeLoadingDialog();
            mLoadingDialog = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoadingDialog();

        EventBusUtil.unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMsg_type() == MessageEvent.BACK_MAIN) {
            this.finish();
        }
    }

    /*
     * 黏性事件处理
     * 子类重载处理完事件后需调用 EventBusUtil.removeStickyEvent(event);
     */
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMessageStickyEvent(MessageEvent event) {

    }

    /**
     * 页面从右面进入
     */
    private static final int WINDOW_EXIT_ANIM_MODE_RIGHT_OUT = 0x110;
    private static final int WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT = 0x111;
    private int currentWindowTransitionMode = -1;

    protected void windowTransitionRightInRightOut() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        currentWindowTransitionMode = WINDOW_EXIT_ANIM_MODE_RIGHT_OUT;
    }

    protected void windowTransitionBottomInBottomOut() {
        overridePendingTransition(R.anim.slide_down_in, R.anim.activity_stay);
        currentWindowTransitionMode = WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT;
    }

    @Override
    public void finish() {
        super.finish();
        if (currentWindowTransitionMode == WINDOW_EXIT_ANIM_MODE_RIGHT_OUT) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        } else if (currentWindowTransitionMode == WINDOW_EXIT_ANIM_MODE_BOTTOM_OUT) {
            overridePendingTransition(0, R.anim.slide_down_out);
        }
    }


}
