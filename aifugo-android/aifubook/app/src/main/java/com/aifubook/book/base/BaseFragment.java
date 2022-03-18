package com.aifubook.book.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.aifubook.book.application.MyApp;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.appcommon.GlobalAppComponent;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.views.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {


    protected MyApp mContext;
    protected View contentView;

    protected LayoutInflater mInflater;
    protected Activity mActivity;
    protected boolean isFirstLoading;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        mContext = (MyApp) mActivity.getApplication();

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventBusUtil.register(this);
        GlobalAppComponent.isAutoForwardLogin = true;
        this.mInflater = inflater;
        if (contentView == null) {
            contentView = inflater.inflate(setContentView(), null);
            isFirstLoading = true;
            initView();
        } else {
            ViewGroup viewGroup = (ViewGroup) contentView.getParent();
            if (null != viewGroup) {
                viewGroup.removeView(contentView);
            }
        }
        return contentView;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoading) {
            loadData();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * true是未登录
     * @return
     */
    protected boolean checkLogin() {
        if (SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
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
    /*
     * 加载布局文件完后找到一个view对象
     */
    protected <T extends View> T findViewById(int resId) {
        return contentView.findViewById(resId);
    }

    /*
     * 数据请求，建议重载保持代码风格统一
     */
    protected void loadData() {
    }

    /*
     * 加载布局文件
     */
    protected abstract int setContentView();

    /*
     * View初始化，建议重载保持代码风格统一
     */
    protected abstract void initView();

}
