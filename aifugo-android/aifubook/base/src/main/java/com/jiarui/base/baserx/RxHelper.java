package com.jiarui.base.baserx;

import android.util.Log;

import com.jiarui.base.baserx.bean.BaseBean;
import com.jiarui.base.baserx.bean.ErrorMessag;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.ConstantUtil;
import com.jiarui.base.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 处理返回的数据
 */
public class RxHelper {

    /**
     * 用来统一处理Http的resultCode,并将返回的Data部分剥离出来返回给Subscriber
     * Listker_hlg
     *
     * @param <T> Subscriber真正需要的数据类型,也就是Data部分的数据类型
     */
    public static <T> Observable.Transformer<BaseBean<T>, T> handleResult() {
        return
                new Observable.Transformer<BaseBean<T>, T>() {
                    @Override
                    public Observable<T> call(Observable<BaseBean<T>> mObservable) {
                        return mObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(BaseBean<T> result) {
                                if (ConstantUtil.SUCCESS.equals(result.getCode())) {
                                    return createData(result.getResult());
                                } else {
                                    if ("501".equals(result.getCode() + "")) {
                                        EventBus.getDefault().post(new MessageEvent());
                                    }
                                    try {
                                        if (!StringUtils.isEmpty(result.getMessage())) {
                                            return Observable.error(new Exception(
                                                    "错误你" + result.getMessage()));
                                        }

                                        if (!StringUtils.isEmpty(((ErrorMessag) result.getResult()).getERROR_Param_Format())) {
                                            return Observable.error(new Exception(
                                                    "错误你" + (((ErrorMessag) result.getResult()).getERROR_Param_Format())));
                                        } else {
                                            return Observable.error(new Exception(
                                                    "错误你接口返回错误"
                                            ));
                                        }
                                    } catch (Exception e) {
                                        Log.e("Exception===", e.getMessage() + "]]]");
                                        return Observable.error(new ApiException(((ErrorMessag) result.getResult()).getERROR_Param_Format()));
                                    }

                                }
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .unsubscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                        //.retryWhen(new RetryWithDelay(2, 1000))//总共重试2次，重试间隔1000毫秒

                    }
                };

    }

    /**
     * 将数据存入Subscriber
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Log.e("XXXXXXXX", "WWWWWWWW" + e);
                    subscriber.onError(e);
                }
            }
        });
    }

}
