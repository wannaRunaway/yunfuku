package com.aifubook.book.activity.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.promptlibrary.PromptDialog;
import com.jiarui.base.status.ImmersionBar;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.views.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @param <P>
 * @author ListKer_Hlg
 */
public abstract class BaseFragment<P extends BasePresenter<?, ?>> extends Fragment {
    protected View rootView;
    public P mPresenter;
    //fragment不可见
//    private boolean isViewVisiable = false;
//    //是否准备
//    private boolean isPrepared = false;
//    //是否加载数据
//    private boolean isDataAdd = false;
//    //非ViewPager
//    private boolean isNoViewpager = false;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean mIsImmersion;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    protected Activity mActivity;
    protected PromptDialog promptDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
    }

    private boolean isFirstLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBusUtil.register(this);

        if (rootView == null) {
            isFirstLoading = true;
            rootView = inflater.inflate(getLayoutResource(), null);
            unbinder = ButterKnife.bind(this, rootView);
            initPresenter();
            if (mPresenter != null) {
                mPresenter.mContext = mActivity;
            }
            mIsImmersion = true;
            initView();
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (null != viewGroup) {
                viewGroup.removeView(rootView);
            }
        }




        return rootView;
    }

    /**
     * false 是登录状态
     * @return
     */
    protected boolean checkLogin() {
        if (SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.e("TAG","isVisibleToUser="+isVisibleToUser+" isFirstLoading="+isFirstLoading);
        if (isVisibleToUser && isFirstLoading) {
            loadData();
        }
    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }


    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    // 获取布局文件
    protected abstract int getLayoutResource();

    // 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    /*
     * View初始化，建议重载保持代码风格统一
     */
    protected abstract void initView();

    /*
     * 数据请求，建议重载保持代码风格统一
     */
    protected void loadData() {
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(getActivity(), text);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(getActivity(),
                text);
    }

    /**
     * 通过Class跳转界面
     **/
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除注解
        EventBusUtil.unregister(this);
        if (unbinder != null)
            unbinder.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
        this.unbinder = null;
        this.mPresenter = null;
        if (promptDialog != null)
            promptDialog.dismiss();
    }


    /**
     * 定时器下拉软键盘
     */
    public void countDownTimer(int time) {
        CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }.start();
    }
}
