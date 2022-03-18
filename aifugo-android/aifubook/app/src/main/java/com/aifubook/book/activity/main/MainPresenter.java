package com.aifubook.book.activity.main;

import com.aifubook.book.bean.UpdateBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.LogUtil;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class MainPresenter extends BasePresenter<MainView, MainModel> {

    public static final String TAG="diMainPresenter";

    public MainPresenter(MainView view) {
        setVM(view, new MainModel());
    }

    public void getUpdate() {
        mRxManage.add(
                mModel.updateApp(new RxSubscriber<UpdateBean>(mContext) {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    protected void _onNext(UpdateBean updateBean) {
                        LogUtil.e(TAG,"update="+updateBean.toString());
                        mView.updateResult(updateBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtil.e(TAG,"update-error="+message);
                    }
                }));
    }


}
