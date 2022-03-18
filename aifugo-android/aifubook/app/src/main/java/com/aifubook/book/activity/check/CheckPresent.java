package com.aifubook.book.activity.check;

import com.aifubook.book.activity.teacherrecords.TeacherRecordsModel;
import com.aifubook.book.activity.teacherrecords.TeacherRecordsView;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.List;
import java.util.Map;

public class CheckPresent extends BasePresenter<CheckView, CheckModel> {
    public CheckPresent(CheckView view) {
        setVM(view, new CheckModel());
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void categoryAll(Map<String, String> requestData) {
        mRxManage.add(
                mModel.categoryAll(requestData, new RxSubscriber<List<TypeBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<TypeBean> DataBean) {
                        mView.stopLoading();
                        mView.GetDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void getByScene(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.getByScene(requestData, new RxSubscriber<SceneBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(SceneBean DataBean) {
                        mView.stopLoading();
                        mView.GetHomePage(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }
}
