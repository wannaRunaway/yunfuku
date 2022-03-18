package com.jiarui.base.baserx;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.jiarui.base.R;
import com.jiarui.base.bases.BaseApplication;
import com.jiarui.base.rxjava.HttpException;
import com.jiarui.base.utils.NetWorkUtils;
import com.jiarui.base.utils.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


/********************
 * 使用例子
 ********************/
/*
 * _apiService.login(mobile, verifyCode) .//省略 .subscribe(new RxSubscriber<User
 * user>(mContext,false) {
 *
 * @Override public void _onNext(User user) { // 处理user }
 *
 * @Override public void _onError(String msg) { ToastUtil.showShort(mActivity,
 * msg); });
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;


    public RxSubscriber(Context context, String msg, boolean showDialog) {
//        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }


    public RxSubscriber() {

    }

    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    @Override
    public void onCompleted() {
//        if (showDialog)
//            LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (showDialog) {
//            try {
//                LoadingDialog.showDialogForLoading((Activity) mContext, msg, true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onNext(T t) {
            _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("RxSubscriber onError==", e.toString() + "---");
        // 网络不可用
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext().getApplicationContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError("请求超时,请稍后再试");
        } else if (e instanceof JsonSyntaxException) {//解析出错
            _onError("解析出错");
        } else if (e instanceof ApiException) {//返回code!=200
            _onError(((ApiException) e).getErrorMsg());
        }
        // 服务器
        else if (e instanceof ServerException) {
            _onError(BaseApplication.getAppContext().getString(R.string.service_error));

        } else if (e instanceof HttpException) {
            _onError("服务器异常，请稍后再试");
        } else if (!StringUtils.isEmpty(e.getMessage()) && e.getMessage().contains("错误你")) {
            _onError(e.getMessage().replace("错误你", ""));
        }
        // 其它
        else {
//            _onError("返回数据异常");
        }

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
