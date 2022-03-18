package com.aifubook.book.mine.self;

import com.aifubook.book.bean.LogoutBean;
import com.aifubook.book.mine.member.LogoutModel;
import com.aifubook.book.mine.member.LogoutView;
import com.aifubook.book.mine.member.MemberInfoModel;
import com.aifubook.book.mine.member.MemberInfoView;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONObject;

public class LogoutPresenter extends BasePresenter<LogoutView, LogoutModel> {

    public LogoutPresenter(LogoutView view) {
        setVM(view, new LogoutModel());
    }

    public void logout(){
        mRxManage.add(
                mModel.logout(new RxSubscriber<String>(mContext) {

                    @Override
                    protected void _onNext(String logoutBean) {
                        LogUtil.e("TAG","logoutBea");
                        mView.getLogoutResult(logoutBean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }));
    }


}
